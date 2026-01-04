package ru.gikexe.the8086mc;

public class ModRM {
	final int mod, reg, rm;

	public ModRM(int modrm) {
		mod = (modrm >> 6) & 3;
		reg = (modrm >> 3) & 7;
		rm = modrm & 7;
	}

	public static final ModRM of(int modrm) {
		return new ModRM(modrm);
	}

	public static final ModRM of(short modrm) {
		return new ModRM(modrm);
	}

	public static final ModRM of(byte modrm) {
		return new ModRM(modrm);
	}
}
