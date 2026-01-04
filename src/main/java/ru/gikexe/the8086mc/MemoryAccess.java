package ru.gikexe.the8086mc;

public interface MemoryAccess {
	int read();

	boolean write(int value);

	boolean inc();

	boolean dec();
}
