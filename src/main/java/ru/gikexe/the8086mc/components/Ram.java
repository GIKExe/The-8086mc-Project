package ru.gikexe.the8086mc.components;

import lombok.Getter;

public abstract class Ram {
	@Getter
	protected int size;
	protected int mask;
	protected byte[] mem;

	public byte getValue(int addr) {
		return mem[addr & mask];
	}

	public void setValue(int addr, byte value) {
		mem[addr & mask] = value;
	}
}
