package ru.gikexe.the8086mc;

public class Processor {
	private Boolean power = false;
	private static final int CF = 0b0000_0000_0000_0001; //  0
																											 //  1
	private static final int PF = 0b0000_0000_0000_0100; //  2
																											 //  3
	private static final int AF = 0b0000_0000_0001_0000; //  4
																											 //  5
	private static final int ZF = 0b0000_0000_0100_0000; //  6
	private static final int SF = 0b0000_0000_1000_0000; //  7
	private static final int TF = 0b0000_0001_0000_0000; //  8
	private static final int IF = 0b0000_0010_0000_0000; //  9
	private static final int DF = 0b0000_0100_0000_0000; // 10
	private static final int OF = 0b0000_1000_0000_0000; // 11
																											 // 12
															 												 // 13
															 												 // 14
															 												 // 15

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
	private int flags = 0b0000_0000_0000_0010;

	// для получения операндов в Mod R/M
	private MemoryByte[] byteReg = { AL, CL, DL, BL, AH, CH, DH, BH, };
	private MemoryWord[] wordReg = { AX, CX, DX, BX, SP, BP, SI, DI, };

	private final Runnable[] optable = {
		() -> ADD(false, 0),
		() -> ADD(true, 0),
		() -> ADD(false, 1),
		() -> ADD(true, 1),
		() -> ADD(false, 2),
		() -> ADD(true, 2),
		this::_0x06,
		this::_0x07,
		this::_0x08,
		this::_0x09,
		this::_0x0A,
		this::_0x0B,
		this::_0x0C,
		this::_0x0D,
		this::_0x0E,
		this::_null,
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
		optable[op].run();
	}

	// добавить проверки на ошибки!
	private int physicAddr(int addr, MemoryWord reg) {
		// физический аддрес = (CS * 16) + IP
		return (reg.read() * 16) + addr;
	}

	private int readByte() {
		int value = (int) memory[physicAddr(IP.read(), CS)];
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

	private MemoryAccess[] getOperands(ModRM modrm, boolean isWord) {
		int rm = modrm.rm;
		MemoryAccess a;
		MemoryAccess b = (isWord ? wordReg[modrm.reg] : byteReg[modrm.reg]);

		if (modrm.mod == 3) {
			a = (isWord ? wordReg[modrm.rm] : byteReg[modrm.rm]);
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
			if (isWord) {
				a = new MemoryWord(memory, physicAddr(addr, CS));
			} else {
				a = new MemoryByte(memory, physicAddr(addr, CS));
			}
		}
		return new MemoryAccess[] {a, b};
	}

	// private void pushByte(int value) {

	// }

	// private int popByte() {

	// }

	private void pushWord(int value) {
		SP.write(SP.read() - 2);
		MemoryWord.of(memory, physicAddr(SP.read(), SS)).write(value);
	}

	private int popWord() {
		return MemoryWord.of(memory, physicAddr(SP.read(), SS)).read();
	}

	private void _null() { // пустая функция

	}

	private void ADD(boolean isWord, int type) {
		int value;
		if (type == 2) {
			if (isWord) {
				value = AX.read() + readWord();
				AX.write(value);
			} else {
				value = AL.read() + readByte();
				AL.write(value);
			}
		} else {
			ModRM modRM = readModRM();
			MemoryAccess[] ops = getOperands(modRM, isWord);
			value = ops[0 + type].read() + ops[1 - type].read();
			ops[0 + type].write(value);
		}
	}

	// http://www.mlsite.net/8086/
	private void _0x00() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		if (ops[0].write(ops[0].read() + ops[1].read()))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x01() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		if (ops[0].write(ops[0].read() + ops[1].read()))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x02() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		if (ops[1].write(ops[1].read() + ops[0].read()))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x03() { // ADD
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		if (ops[1].write(ops[1].read() + ops[0].read()))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x04() { // ADD
		int value = readByte();
		if (AL.write(AL.read() + value))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x05() { // ADD
		int value = readWord();
		if (AX.write(AX.read() + value))
			flags |= CF;
		else
			flags &= ~CF;
	}

	private void _0x06() { // PUSH ES
		pushWord(ES.read());
	}

	private void _0x07() { // POP ES
		ES.write(popWord());
	}

	private void _0x08() { // OR
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		ops[0].write(ops[0].read() | ops[1].read());
	}

	private void _0x09() { // OR
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		ops[0].write(ops[0].read() | ops[1].read());
	}

	private void _0x0A() { // OR
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, false);
		ops[1].write(ops[1].read() | ops[0].read());
	}

	private void _0x0B() { // OR
		ModRM modRM = readModRM();
		MemoryAccess[] ops = getOperands(modRM, true);
		ops[1].write(ops[1].read() | ops[0].read());
	}

	private void _0x0C() { // OR
		int value = readByte();
		AL.write(AL.read() | value);
	}

	private void _0x0D() { // OR
		int value = readWord();
		AX.write(AX.read() | value);
	}

	private void _0x0E() { // PUSH CS
		pushWord(CS.read());
	}

	// удалён в процессорах следующего поколения
	// private void _0x0F() { // POP CS
	// 	CS.write(popWord());
	// }

	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}
	// private void _0x06() {}

}
