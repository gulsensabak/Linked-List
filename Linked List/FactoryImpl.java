import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Handler;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.print.attribute.Size2DSyntax;
import javax.xml.transform.Source;

public abstract class FactoryImpl implements Factory{
	private Holder first;
	private Holder last;
	private Integer size;

	
	
	public FactoryImpl() {
		this.size=0;
	}
	@Override
	public void addFirst(Product product) {
		if(size == 0) {
			Holder nextFirst= new Holder(null, product, null);
			first = nextFirst;
			last = nextFirst;
			size = 1;
			return;
		}

		if(size == 1) {
			Holder nextFirst = new Holder(null, product, null);
			nextFirst.setNextHolder(first);
			first.setPreviousHolder(nextFirst);
			last = first;
			first = nextFirst;
			size++;
			return;
		}
		if( size >1) {
			Holder nextFirst = new Holder(null, product, null);
			nextFirst.setNextHolder(first);
			first.setPreviousHolder(nextFirst);
			first = nextFirst;
			size++;
			return;
		}

	}

	@Override
	public void addLast(Product product) {
		if(size == 0) {
			Holder nextLast= new Holder(null, product, null);
			first = nextLast;
			last = nextLast;
			size =1;
			return;
		}
		if(size == 1) {
			Holder nextLast = new Holder(null, product, null);
			nextLast.setPreviousHolder(last);

			last.setNextHolder(nextLast);
			first = last;
			last = nextLast;
			size++;
			return;
		}


		else if(size > 1) {
			Holder nextLast = new Holder(null, product, null);
			nextLast.setPreviousHolder(last);
			last.setNextHolder(nextLast);
			last = nextLast;
			size++;
			return;
			
			
		}

	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		if(size == 0) {
			throw new NoSuchElementException("Factory is empty.");
		}
		else if(size == 1) {
			Product removed = this.first.getProduct();
			size =0 ;
			first=null;
			last=null;
			return removed;
		}
		else{
			Holder temHolder = first;
			Holder afterHolder = first.getNextHolder();
			afterHolder.setPreviousHolder(null);
			first = afterHolder;
			size--;

			return temHolder.getProduct();
		}

	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		
		if(first == null && last == null) {
			throw new NoSuchElementException("Factory is empty.");
		}
		
		if (size==1) {
			Product removedProduct = first.getProduct();
			first=null;
			size=0;
			this.last=null;
			this.first=null;
			return removedProduct;
			
		}

		else if(last.getPreviousHolder() != null){
			Holder templHolder = last;
			Holder preLastHolder = last.getPreviousHolder();
			preLastHolder.setNextHolder(null);
			last = preLastHolder;
			size--;
			return templHolder.getProduct();
		}

		return null;
	}

	@Override
	public Product find(int id) throws NoSuchElementException {
		Holder temHolder = first;
		if(temHolder == null) {
			throw new NoSuchElementException("Product not found.");
		}
		
		if(temHolder.getNextHolder() == null) {
			if(temHolder.getProduct().getId() == id) {
				return temHolder.getProduct();
			}
			else {
				throw new NoSuchElementException("Product not found.");
			}
		}
		else if(temHolder.getNextHolder() != null) {
			
			
			while(temHolder.getNextHolder()!= null) {
				if(temHolder.getProduct().getId() == id) {
					return temHolder.getProduct();
				}
				temHolder = temHolder.getNextHolder();
			}
			if(temHolder.getNextHolder()== null && temHolder.getProduct().getId() == id) {
				return temHolder.getProduct();
			}
			else if(temHolder.getNextHolder()== null && temHolder.getProduct().getId() != id) {
				throw new NoSuchElementException("Product not found.");
			}
		}

		return null;
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {

		Holder temHolder = first;
		if(temHolder == null) {
			throw new NoSuchElementException("Product not found.");
		}
		if(temHolder.getNextHolder() == null) {
			if(temHolder.getProduct().getId() == id) {
				Product temProduct = new Product(id, temHolder.getProduct().getValue());
				//int myVal = temHolder.getProduct().getValue(); 
				temHolder.getProduct().setValue(value);
				Product lastProduct = new Product(id, temProduct.getValue());
				return lastProduct;
			}
			else {
				throw new NoSuchElementException("Product not found.");
			}
		}
		else if(temHolder.getNextHolder() != null) {
			while(temHolder.getNextHolder()!= null) {
				if(temHolder.getProduct().getId() == id) {
					Product temProduct = new Product(id, temHolder.getProduct().getValue());
					temHolder.getProduct().setValue(value);
					Product lastProduct = new Product(id, temProduct.getValue());
					return lastProduct;
				}
				temHolder = temHolder.getNextHolder();
			}
			if(temHolder.getNextHolder()== null && temHolder.getProduct().getId() == id) {
				Product temProduct = new Product(id, temHolder.getProduct().getValue());
				temHolder.getProduct().setValue(value);
				Product lastProduct = new Product(id, temProduct.getValue());
				return lastProduct;
			}
			else if(temHolder.getNextHolder()== null && temHolder.getProduct().getId() != id) {
				throw new NoSuchElementException("Product not found.");
			}
		}

		return null;
	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		if(index >= size || size == 0) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		else {
			Holder tempFirst = first;
			
			for (int i = 0; i < index; i++) {
				tempFirst=tempFirst.getNextHolder();
			}
			return tempFirst.getProduct();
		}	
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		if( index == 0) {
			addFirst(product);
			return;
		}
		else if(size == null) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
				
		else if(index == size) {
			addLast(product);
			return;
		}

		else if(index> size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
			
		}
		else {
			Holder tempFirdtHolder = first;
			for (int i = 0; i < index; i++) {
				tempFirdtHolder = tempFirdtHolder.getNextHolder();				
			}

			Holder newHolder = new Holder(tempFirdtHolder.getPreviousHolder(), product, tempFirdtHolder);
			tempFirdtHolder.getPreviousHolder().setNextHolder(newHolder);
			tempFirdtHolder.setPreviousHolder(newHolder);
			size++;
			return;
		}
	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if(first == null) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		if(index==size-1) { 
			Product removed = removeLast();
			return removed;
		}
		
		if (index==0) {
			Product removed = this.first.getProduct();
			removeFirst();
			return removed;
			
		}
		if(index >= size || first == null) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}

		else {
			Holder tempHandler = first;
			for (int i = 0; i < index; i++) {
				tempHandler = tempHandler.getNextHolder();			
			}
			if(tempHandler.getNextHolder() == null) {
				tempHandler.getPreviousHolder().setNextHolder(null);
				size--;	
				return tempHandler.getProduct();
			}
			else {
				tempHandler.getPreviousHolder().setNextHolder(tempHandler.getNextHolder());
				tempHandler.getNextHolder().setPreviousHolder(tempHandler.getPreviousHolder());
				size--;	
				return tempHandler.getProduct();
			}
		}
	}

	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		Holder temHolder = first;		
		if(temHolder == null) {
			throw new NoSuchElementException("Product not found.");
		}
		
		while(temHolder!= null) {
			if(temHolder.getProduct().getValue() == value) {
				Holder tem2 = temHolder;
				if(temHolder.getNextHolder()== null) {
					removeLast();
				}
				else if(temHolder.getPreviousHolder() == null) {
					removeFirst();
				}
				else {
					temHolder.getPreviousHolder().setNextHolder(temHolder.getNextHolder());
					temHolder.getNextHolder().setPreviousHolder(temHolder.getPreviousHolder());
					size--;
				}
				
				return tem2.getProduct();
			}
			else {
				temHolder = temHolder.getNextHolder();
			}
		}
		
		throw new NoSuchElementException("Product not found.");
	}

	@Override
	public int filterDuplicates() {
		if(this.size != 0) {
			Holder temHolder = first;
			ArrayList<Integer> valuesOfProduct = new ArrayList<>();
			int counter = 0;
			int counterOfIndex = 0;
			while(true) {
				if(valuesOfProduct.contains(temHolder.getProduct().getValue())) {
					removeIndex(counterOfIndex);
					counter++;
					counterOfIndex--;
				}
				else {
					valuesOfProduct.add(temHolder.getProduct().getValue());
				}
				if(temHolder.getNextHolder() != null) {
					temHolder=temHolder.getNextHolder();
				}
				else {
					return counter;
				}
				counterOfIndex++;
			}
		}
		return 0;
		
	}


	@Override
	public void reverse() {

		Holder tempHolder = this.first;
		
		if (this.size==0 || this.size==1 || tempHolder == null) {
			Print();
			return;
		}
		
		else {
			for (int i = 0; i < size-1; i++) {
				Holder nextHolder = tempHolder.getNextHolder();
				tempHolder.setNextHolder(tempHolder.getPreviousHolder());
				tempHolder.setPreviousHolder(nextHolder);
				tempHolder=nextHolder;
			}
			tempHolder.setNextHolder(tempHolder.getPreviousHolder());
			tempHolder.setPreviousHolder(null);
			Holder tempFirstHolder = this.first;
			this.first = this.last;
			this.last=tempFirstHolder;
			Print();
		}

	}



	public void Print() {
		System.out.print("{");
		Holder tempoHolder = first;
		if(tempoHolder == null) {
			System.out.println("}");
		}
		else {
			while(tempoHolder.getNextHolder() != null) {
				System.out.print(tempoHolder.getProduct()+ ",");
				tempoHolder = tempoHolder.getNextHolder();
			}
			if(tempoHolder.getNextHolder() == null) {
				System.out.print(tempoHolder.getProduct());

			}
			System.out.println("}");
		}
	}


}