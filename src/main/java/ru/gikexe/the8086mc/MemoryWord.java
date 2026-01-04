package ru.gikexe.the8086mc;

public class MemoryWord implements MemoryAccess {
	private byte[] memory;
	private int offset;

	public MemoryWord(byte[] memory, int offset) {
		this.memory = memory;
		this.offset = offset;
	}

	public static final MemoryWord of(byte[] memory, int offset) {
		return new MemoryWord(memory, offset);
	}

	@Override
	public int read() {
		return (memory[offset] & 0xFF) | ((memory[offset+1] & 0xFF) << 8);
	}

	@Override
	public boolean write(int value) {
		memory[offset] = (byte) value;
		memory[offset+1] = (byte) (value >> 8);
		return (value >>> 16) > 0;
	}

	@Override
	public boolean inc() {
		return write(read() + 1);
	}

	@Override
	public boolean dec() {
		return write(read() - 1);
	}
}
