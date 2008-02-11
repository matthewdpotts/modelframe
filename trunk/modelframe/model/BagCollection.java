package model;

import java.util.ArrayList;
import model.MersenneTwisterFast;

public class BagCollection < E > extends ArrayList{
	
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
