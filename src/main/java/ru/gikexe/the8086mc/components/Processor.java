package ru.gikexe.the8086mc.components;

public class Processor {
	private Register ax, bx, cx, dx;
	private Register al, bl, cl, dl;
	private Register ah, bh, ch, dh;
	private Register sp, bp;
	private Register si, di;
	private Register es, cs, ss, ds;
	private Register ip;
	private Motherboard motherboard;

	public Processor() {
		init();
	}

	public Processor(Motherboard motherboard) {
		setMotherboard(motherboard);
	}

	// является переустановкой процессора, состояние должно сброситься.
	public Processor setMotherboard(Motherboard motherboard) {
		this.motherboard = motherboard;
		init();
		return this;
	}

	private void init() {
		ax = new RegisterFull();
		al = new RegisterLow(ax);
		ah = new RegisterHigh(ax);

		bx = new RegisterFull();
		bl = new RegisterLow(bx);
		bh = new RegisterHigh(bx);

		cx = new RegisterFull();
		cl = new RegisterLow(cx);
		ch = new RegisterHigh(cx);

		dx = new RegisterFull();
		dl = new RegisterLow(dx);
		dh = new RegisterHigh(dx);

		sp = new RegisterFull();
		bp = new RegisterFull();
		ip = new RegisterFull();

		si = new RegisterFull();
		di = new RegisterFull();

		es = new RegisterFull();
		cs = new RegisterFull();
		ss = new RegisterFull();
		ds = new RegisterFull();
	}

	public void tick() {
		byte op = readNextOpcode();
		ip.inc();
		switch (op) {

		}
	}

	private Register selectRegister(char number) {
		return switch (number) {
			case 0 -> al;
			case 1 -> cl;
			case 2 -> dl;
			case 3 -> bl;
			case 4 -> ah;
			case 5 -> ch;
			case 6 -> dh;
			case 7 -> bh;
			default -> null;
		};
	}

	private byte readNextOpcode() {
		return motherboard.getRamValue(getRealAddr(cs.getValue(), ip.getValue()));
	}

	private int getRealAddr(short base, short shift) {
		return (((int)base << 4) + shift) & 0xFFFFF;
	}
}
