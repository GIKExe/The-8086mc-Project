package ru.gikexe.the8086mc;

public interface MemoryAccess {
	int read();

	void write(int value);

	void inc();

	void dec();
}
