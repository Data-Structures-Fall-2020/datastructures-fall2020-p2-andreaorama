package edu.uprm.cse.ds.sortedlist;


import java.util.Iterator;

public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;

        }

        public Node() {
            this.element = null;
            this.next = null;
            this.prev = null;

        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    //SortedCircularDoublyLinkedListIterator Class
    public class SCDLLIterator implements Iterator<E>{

        private Node<E> next;

        public SCDLLIterator(){
            this.next = header.getNext();
        }
        @Override
        public boolean hasNext() {
            return this.next.getElement() != null;
        }

        @Override
        public E next() {
            if(this.hasNext()){
                E result = this.next.getElement();
                this.next = this.next.getNext();
                return result;
            }
            else{
                return null;
            }
        }
    }

    //SortedCircularDoublyLinkedListReveredIterator Class
    public class SCDLLReverseIterator implements ReverseIterator<E>{

        private Node<E> prev;

        public SCDLLReverseIterator(){
            this.prev = header.getPrev();
        }
        @Override
        public boolean hasPrevious() {
            return this.prev.getElement() != null;
        }

        @Override
        public E previous() {
            if(this.hasPrevious()){
                E result = this.prev.getElement();
                this.prev = this.prev.getPrev();
                return result;
            }
            else{
                return null;
            }
        }
    }

    private final Node<E> header;
    private int currentSize;

    public SortedCircularDoublyLinkedList() {

            this.currentSize = 0;
            this.header = new Node<>();

            this.header.setNext(this.header);
            this.header.setPrev(this.header);

    }

    @Override
    public boolean add(E obj) {
        Node<E> newNode = new Node<>();
        newNode.setElement(obj);
        if(obj == null){
            return false;
        }
        else if(this.isEmpty()){
            header.setNext(newNode);
            header.setPrev(newNode);
            newNode.setNext(this.header);
            newNode.setPrev(this.header);
            this.currentSize++;
        }
        else {
            boolean added = false;
            Node<E> temp = this.header;
            E before;
            Iterator<E> iterator = new SCDLLIterator();
            while(iterator.hasNext()){
                before = iterator.next();
                temp = temp.getNext();
                if(before.compareTo(obj) >= 0){
                    newNode.setPrev(temp.getPrev());
                    newNode.setNext(temp);
                    temp.getPrev().setNext(newNode);
                    temp.setPrev(newNode);
                    this.currentSize++;
                    added = true;
                    break;
                }
            }
            if(!added){
                newNode.setPrev(this.header.getPrev());
                newNode.setNext(this.header);
                this.header.getPrev().setNext(newNode);
                this.header.setPrev(newNode);
                this.currentSize++;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public boolean remove(E obj) {
        return remove(firstIndex(obj));
    }

    @Override
    public boolean remove(int index) {
        if(index > currentSize - 1){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        else{
            Node<E> temp = this.header;
            Iterator<E> iterator = new SCDLLIterator();
            while(iterator.hasNext()){
                temp = temp.getNext();
                if(index == 0){
                    temp.getPrev().setNext(temp.getNext());
                    temp.getNext().setPrev(temp.getPrev());
                    temp.setPrev(null);
                    temp.setNext(null);
                    this.currentSize--;
                    return true;
                }
                index--;
            }
            return false;
        }
    }

    @Override
    public int removeAll(E obj) {
        int count = 0;
        if(firstIndex(obj) < 0){
            return count;
        }
        else{
            while(firstIndex(obj) >= 0){
                count ++;
                remove(firstIndex(obj));
            }
        }
        return count;
    }

    @Override
    public E first() {
        return this.header.getNext().getElement();
    }

    @Override
    public E last() {
        return this.header.getPrev().getElement();
    }

    @Override
    public E get(int index) {
        if(index > currentSize - 1){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        else{
            E temp;
            E result;
            Iterator<E> iterator = new SCDLLIterator();
            while(iterator.hasNext()){
                temp = iterator.next();
                if(index == 0){
                    result = temp;
                    return result;
                }
                else{
                    index--;
                }

            }
            return null;
        }
    }

    @Override
    public void clear() {
        while(this.first() != null){
            remove(first());
        }
    }

    @Override
    public boolean contains(E e) {
        Iterator<E> iterator = new SCDLLIterator();
        while(iterator.hasNext()){
            E temp = iterator.next();
            if(temp.equals(e)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    @Override
    public Iterator<E> iterator(int index) {
        Iterator<E> result = new SCDLLIterator();
        if(index > currentSize - 1){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        else{
            while(index != 0){
                result.next();
                index--;
            }
        }
        return result;
    }

    @Override
    public int firstIndex(E e) {
        int index = 0;
        if(this.contains(e)){
            Iterator<E> iterator = new SCDLLIterator();
            while(iterator.hasNext()){
                E next = iterator.next();
                if(next.equals(e)){
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndex(E e) {
        int index = this.currentSize;
        //Paso 1 - Verificar si la lista contiene el objeto
        //Paso 2- Si lo tiene iterar en reverso por la lista hasta encontrarlo
        //        disminuyendo un count
        if(this.contains(e)){
            ReverseIterator<E> revIterator = new SCDLLReverseIterator();
            while(revIterator.hasPrevious()){
                index--;
                E prev = revIterator.previous();
                if(prev.equals(e)){
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public ReverseIterator<E> reverseIterator() {
        return new SCDLLReverseIterator();
    }

    @Override
    public ReverseIterator<E> reverseIterator(int index) {
        ReverseIterator<E> result = new SCDLLReverseIterator();
        if(index > currentSize - 1){
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        else{
            int lastIndex = this.currentSize - 1;
            while(index != lastIndex){
                result.previous();
                index++;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new SCDLLIterator();
    }
}
