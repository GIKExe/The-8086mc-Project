package ru.gikexe.the8086mc;

public class MemoryByte implements MemoryAccess {
	private byte[] memory;
	private int offset;

	public MemoryByte(byte[] memory, int offset) {
		this.memory = memory;
		this.offset = offset;
	}

	@Override
	public int read() {
		return memory[offset] & 0xFF;
	}

	@Override
	public void write(int value) {
		memory[offset] = (byte) value;
	}

	@Override
	public void inc() {
		write(read()+1);
	}

	@Override
	public void dec() {
		write(read()-1);
	}
}
