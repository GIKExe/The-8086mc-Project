package ru.gikexe.the8086mc;

public class MemoryWord implements MemoryAccess {
	private byte[] memory;
	private int offset;

	public MemoryWord(byte[] memory, int offset) {
		this.memory = memory;
		this.offset = offset;
	}

	@Override
	public int read() {
		return (memory[offset] & 0xFF) | ((memory[offset+1] & 0xFF) << 8);
	}

	@Override
	public void write(int value) {
		memory[offset] = (byte) value;
		memory[offset+1] = (byte) (value >> 8);
	}

	@Override
	public void inc() {
		write(read() + 1);
	}

	@Override
	public void dec() {
		write(read() - 1);
	}
}
