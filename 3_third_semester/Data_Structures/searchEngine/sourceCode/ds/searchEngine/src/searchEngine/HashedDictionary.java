package searchEngine;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedDictionary<K, V> implements HashedDictionaryInterface<K, V> {
	private TableEntry<K, V>[] hashTable; 
	private int numberOfEntries;
	private int locationsUsed; 
	private int searchCollisionCounter = 0;
	private static final int DEFAULT_SIZE = 2477; 
	private double MAX_LOAD_FACTOR = 0.8;

	public HashedDictionary() {
		this(DEFAULT_SIZE); 
	} 

	@SuppressWarnings("unchecked")
	public HashedDictionary(int tableSize) {
		int primeSize = getNextPrime(tableSize);
		hashTable = new TableEntry[primeSize];
		numberOfEntries = 0;
		locationsUsed = 0;
		searchCollisionCounter = 0;
	}
	
	public double getMaxLoadFactor() {
		return MAX_LOAD_FACTOR;
	}
	
	public int getHashTableSize() {
		return hashTable.length;
	}
	
	public void setMaxLoadFactor(double size) {
		MAX_LOAD_FACTOR = size;
	}
	
	public int getHashValue(K word,String HashFunction) {
		if (HashFunction == "ssf") {
			return getHashIndexSSF(word);
		}
		else {
			return getHashIndexPAF(word);
		}
	}
	
	public void getSearchCollisionCount() {
		System.out.println(searchCollisionCounter);
	}
	
	public boolean isPrime(int num) {
		boolean prime = true;
		for (int i = 2; i <= num / 2; i++) {
			if ((num % i) == 0) {
				prime = false;
				break;
			}
		}
		return prime;
	}

	public int getNextPrime(int num) {
		if (num <= 1)
            return 2;
		else if(isPrime(num))
			return num;
        boolean found = false;   
        while (!found)
        {
            num++;     
            if (isPrime(num))
                found = true;
        }     
        return num;
	}

	public V add(K key, V value, String HashFunction, String collisionHandling) {
		V oldValue; 
		boolean validEntry = true;
		if (!HashFunction.equalsIgnoreCase("paf")&&!HashFunction.equalsIgnoreCase("ssf") || 
			(!collisionHandling.equalsIgnoreCase("lp") && !collisionHandling.equalsIgnoreCase("dh"))) {
			validEntry = false;
		}
		if (isHashTableTooFull() && validEntry)
			rehash(HashFunction,collisionHandling);
		
		int index = 0;
		if (validEntry && HashFunction.equals("paf")) index = getHashIndexPAF(key);
		else if(validEntry && HashFunction.equals("ssf")) index = getHashIndexSSF(key);
		
		if (validEntry && collisionHandling.equals("lp")) index = probe(index, key); 
		else if(validEntry && collisionHandling.equals("dh")) index = doubleHashing(index, key);

		if ((hashTable[index] == null) || hashTable[index].isRemoved()) { 
			hashTable[index] = new TableEntry<K, V>(key, value);
			numberOfEntries++;
			locationsUsed++;
			oldValue = null;
		}
		else { 
			oldValue = hashTable[index].getValue();
			hashTable[index].setValue(value);
		} 
		return oldValue;
	}

	private int getHashIndexSSF(K key) {
		// explanation: A's hash code 65
		// a's hash code 97	
		// 97%32 = 1 && 65%32 = 1
		String keyString = key.toString();
		int hashIndex = 0; 
		Character letterHash;
		for (char letter : keyString.toCharArray()) {
			letterHash = letter;		
			int tempHashIndex = letterHash.hashCode()%32 % hashTable.length; 
			hashIndex += tempHashIndex;
		}
		if (hashIndex < 0)									  	
			hashIndex = hashIndex + hashTable.length; 
		return hashIndex;
	}

	private int getHashIndexPAF(K key) {
		String keyString = key.toString();
		int hashIndex = 0;
		int prime = 31;
		for (int i = 0; i < keyString.length(); i++) {
			int letterIndex = keyString.charAt(i) % 32;
			hashIndex = (letterIndex + prime * hashIndex) % hashTable.length;
		}
		
		if (hashIndex < 0)									  	
			hashIndex = hashIndex + hashTable.length;
		return hashIndex % hashTable.length;
	}

	public boolean isHashTableTooFull() {
		double load_factor = (double) locationsUsed / hashTable.length;
		if (load_factor >= MAX_LOAD_FACTOR)
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public void rehash(String HashFunction, String collisionHandling) {
		TableEntry<K, V>[] oldTable = hashTable;
		int oldSize = hashTable.length;
		int newSize = getNextPrime(2 * oldSize);
		hashTable = new TableEntry[newSize]; 
		numberOfEntries = 0; 
		locationsUsed = 0;

		for (int index = 0; index < oldSize; index++) {
			if (oldTable[index] != null && oldTable[index].isIn()) {
				add(oldTable[index].getKey(), oldTable[index].getValue(), HashFunction, collisionHandling);
			}
		}
	}

	private int doubleHashing(int index, K key) {
		boolean found = false;
		int removedStateIndex = -1; 
		int firstFunction = index % hashTable.length;
		int secondFunction = 31 - (index%31);
		while (!found && (hashTable[firstFunction] != null)) {
			if (hashTable[firstFunction].isIn()) {
				if (key.equals(hashTable[firstFunction].getKey())) {
					found = true; 
					}
				else {
					firstFunction += secondFunction ;
					firstFunction = firstFunction% hashTable.length; 			
				}
			} 
			else 
			{
				if (removedStateIndex == -1)
					removedStateIndex = firstFunction;
				firstFunction += secondFunction; 
				firstFunction = firstFunction% hashTable.length; 
			} 
		} 
		if (found || (removedStateIndex == -1))
			return firstFunction; 
		else
			return removedStateIndex; 
	}
	
	private int probe(int index, K key) {
		boolean found = false;
		int removedStateIndex = -1; 
		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn()) {
				if (key.equals(hashTable[index].getKey())) {
					found = true; 
					}
				else {
					index = (index + 1) % hashTable.length; 
				}
			} 
			else 
			{
				if (removedStateIndex == -1)
					removedStateIndex = index;
				index = (index + 1) % hashTable.length; 
			} 
		} 
		if (found || (removedStateIndex == -1))
			return index; 
		else
			return removedStateIndex; 
	}

	private int locate(int index, K key, String collisionHandling, boolean increaseCollisionCount) {
		boolean found = false;
		int hash = index;
		int doubleHash = 31 - (index % 31);
		int d = doubleHash;

		while (!found && (hashTable[index] != null)) {
			if (hashTable[index].isIn() && key.equals(hashTable[index].getKey()))
				found = true; 
			else if (collisionHandling.equalsIgnoreCase("lp")){
				index = (index + 1) % hashTable.length;
				index = index % hashTable.length;
				if (increaseCollisionCount) {
					searchCollisionCounter++;
				}
			}
			else if (collisionHandling.equalsIgnoreCase("dh")) {
				
				index = (hash + d) % hashTable.length;
				d += doubleHash;

				if (increaseCollisionCount) {
					searchCollisionCounter++;
				}
			}
		} 
		int result = -1;
		if (found)
			result = hash;
		return result;
	}
	
	public V getValue(K key, String HashFunction, String collisionHandling, boolean increaseCollisionCount) {
		V result = null;
		int index = 0;
		if (HashFunction.equalsIgnoreCase("ssf")) index = getHashIndexSSF(key);
		else if (HashFunction.equalsIgnoreCase("paf")) index = getHashIndexPAF(key);
		index = locate(index, key, collisionHandling, increaseCollisionCount);
		if (index != -1)
			result = hashTable[index].getValue(); 
		return result;
	}

	public boolean contains(K key, String HashFunction, String collisionHandling, boolean increaseCollisionCount) {
		int index = 0;
		if (HashFunction.equalsIgnoreCase("ssf")) index = getHashIndexSSF(key);
		else if (HashFunction.equalsIgnoreCase("paf")) index = getHashIndexPAF(key);
		index = locate(index, key, collisionHandling, increaseCollisionCount);
		if (index != -1)
			return true;
		return false;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public int getSize() {
		return numberOfEntries;
	}
	
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	private class TableEntry<S, T> {
		private S key;
		private T value;
		private boolean inTable;

		private TableEntry(S key, T value) {
			this.key = key;
			this.value = value;
			inTable = true;
		}

		private S getKey() {
			return key;
		}

		private T getValue() {
			return value;
		}

		private void setValue(T value) {
			this.value = value;
		}

		private boolean isRemoved() {
			return inTable == false;
		}

		@SuppressWarnings("unused")
		private void setToRemoved() {
			inTable = false;
		}

		private boolean isIn() {
			return inTable == true;
		}
	}

	private class KeyIterator implements Iterator<K> {
		private int currentIndex; 
		private int numberLeft; 

		private KeyIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public K next() {
			K result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getKey();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
	
	private class ValueIterator implements Iterator<V> {
		private int currentIndex; 
		private int numberLeft; 

		private ValueIterator() {
			currentIndex = 0;
			numberLeft = numberOfEntries;
		} 

		public boolean hasNext() {
			return numberLeft > 0;
		} 

		public V next() {
			V result = null;
			if (hasNext()) {
				while ((hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved()) {
					currentIndex++;
				} 
				result = hashTable[currentIndex].getValue();
				numberLeft--;
				currentIndex++;
			} else
				throw new NoSuchElementException();
			return result;
		} 

		public void remove() {
			throw new UnsupportedOperationException();
		} 
	}
}
