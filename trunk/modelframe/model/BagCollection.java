package model;

import java.util.ArrayList;
import model.util.MersenneTwisterFast;

public class BagCollection < E > extends ArrayList < E > {
	
	private MersenneTwisterFast mt;
	
	public BagCollection() {
		super();
		mt = new MersenneTwisterFast();
	}
	
	public BagCollection(int size) {
		super(size);
		mt = new MersenneTwisterFast();
	}
	
	public E get() {
		int index = mt.nextInt(this.size());
		return (E) super.get(index);
	}
	
	public E getAndRemove() {
		E elem = get();
		remove(elem);
		return elem;
	}
	
}
