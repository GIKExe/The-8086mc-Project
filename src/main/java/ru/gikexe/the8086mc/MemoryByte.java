package ru.gikexe.the8086mc;

public class MemoryByte implements MemoryAccess {
	private byte[] memory;
	private int offset;

	public MemoryByte(byte[] memory, int offset) {
		this.memory = memory;
		this.offset = offset;
	}

	public static final MemoryByte of(byte[] memory, int offset) {
		return new MemoryByte(memory, offset);
	}

	@Override
	public int read() {
		return memory[offset] & 0xFF;
	}

	@Override
	public boolean write(int value) {
		memory[offset] = (byte) value;
		return (value >>> 8) > 0;
	}

	@Override
	public boolean inc() {
		return write(read()+1);
	}

	@Override
	public boolean dec() {
		return write(read()-1);
	}
}
