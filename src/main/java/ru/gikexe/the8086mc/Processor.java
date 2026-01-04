package ru.gikexe.the8086mc;

public class Processor {
	private Boolean power = false;
	private byte[] memory;
	private byte[] registers = {
		0, 0, // AL, AH = AX 0
		0, 0, // BL, BH = BX 2
		0, 0, // CL, CH = CX 4
		0, 0, // DL, DH = DX 6
		0, 0, // SI 8
		0, 0, // DI 10
		0, 0, // BP 12
		0, 0, // SP 14
		0, 0, // CS 16
		0, 0, // DS 18
		0, 0, // ES 20
		0, 0, // SS 22
		0, 0, // IP 24
	};

	private MemoryByte AL = new MemoryByte(registers, 0);
	private MemoryByte AH = new MemoryByte(registers, 1);
	private MemoryWord AX = new MemoryWord(registers, 0);

	private MemoryByte BL = new MemoryByte(registers, 2);
	private MemoryByte BH = new MemoryByte(registers, 3);
	private MemoryWord BX = new MemoryWord(registers, 2);

	private MemoryByte CL = new MemoryByte(registers, 4);
	private MemoryByte CH = new MemoryByte(registers, 5);
	private MemoryWord CX = new MemoryWord(registers, 4);

	private MemoryByte DL = new MemoryByte(registers, 6);
	private MemoryByte DH = new MemoryByte(registers, 7);
	private MemoryWord DX = new MemoryWord(registers, 6);

	private MemoryWord SI = new MemoryWord(registers, 8);
	private MemoryWord DI = new MemoryWord(registers, 10);
	private MemoryWord BP = new MemoryWord(registers, 12);
	private MemoryWord SP = new MemoryWord(registers, 14);

	private MemoryWord CS = new MemoryWord(registers, 16);
	private MemoryWord DS = new MemoryWord(registers, 18);
	private MemoryWord ES = new MemoryWord(registers, 20);
	private MemoryWord SS = new MemoryWord(registers, 22);

	private MemoryWord IP = new MemoryWord(registers, 24);
	private int flags = 0;

	// для получения операндов в Mod R/M
	private MemoryByte[] byteReg = { AL, CL, DL, BL, AH, CH, DH, BH, };
	private MemoryWord[] wordReg = { AX, CX, DX, BX, SP, BP, SI, DI, };

	private final Runnable[] optable = {
		this::_0x00,
		this::_0x01,
		this::_0x02,
		this::_0x03,
		this::_0x04,
		this::_0x05,
	};

	public Processor() {

	}

	public void setPower(Boolean value) {
		power = value;
	}

	public Boolean getPower() {
		return power;
	}

	public void clockСycle() {
		int op = readByte();
	}

	// добавить проверки на ошибки!
	private int physicAddr(int addr) {
		// физический аддрес = (CS * 16) + IP
		return (CS.read() * 16) + addr;
	}

	private int readByte() {
		int value = (int) memory[physicAddr(IP.read())];
		IP.inc();
		return (int) value;
	}

	private int readWord() {
		int value = readByte();
		return value + (readByte() << 8);
	}

	private ModRM readModRM() {
		return ModRM.of(readByte());
	}

	private MemoryAccess[] getOperands(ModRM modrm, boolean itword) {
		int rm = modrm.rm;
		MemoryAccess a;
		MemoryAccess b = (itword ? wordReg[modrm.reg] : byteReg[modrm.reg]);

		if (modrm.mod == 3) {
			a = (itword ? wordReg[modrm.rm] : byteReg[modrm.rm]);
		} else {
			int addr = 0;
			if (rm == 0 || rm == 1 || rm == 7) addr += BX.read();
			if (rm == 2 || rm == 3 || rm == 6) addr += BP.read();
			if (rm == 0 || rm == 2 || rm == 4) addr += SI.read();
			if (rm == 1 || rm == 3 || rm == 5) addr += DI.read();
			switch (modrm.mod) {
				case 2: addr += readWord(); break;
				case 1: addr += readByte(); break;
				case 0: if (rm == 6) addr = readWord();
			}
			if (itword) {
				a = new MemoryWord(memory, physicAddr(addr));
			} else {
				a = new MemoryByte(memory, physicAddr(addr));
			}
		}
		return new MemoryAccess[] {a, b};
	}

	private void _null() { // пустая функция

	}

	// http://www.mlsite.net/8086/
	private void _0x00() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		ops[0].write(ops[0].read() + ops[1].read());
	}

	private void _0x01() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		ops[0].write(ops[0].read() + ops[1].read());
	}

	private void _0x02() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		ops[1].write(ops[1].read() + ops[0].read());
	}

	private void _0x03() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		ops[1].write(ops[1].read() + ops[0].read());
	}

	private void _0x04() { // ADD
		int value = readByte();
		AL.write(value);
	}

	private void _0x05() {
		int value = readWord();
		AX.write(value);
	}
}
