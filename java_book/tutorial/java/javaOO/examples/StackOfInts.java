/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

public class StackOfInts {
	
	private int[] stack;
	private int next = 0;  // index of last item in stack + 1
	
	public StackOfInts(int size) {
		//create an array large enough to hold the stack
		stack = new int[size];
	}
	
	public void push(int on) {
		if (next < stack.length)
		   stack[next++] = on;
	}
	public boolean isEmpty() {
		return (next == 0);
	}
	
	public int pop(){
		if (!isEmpty()) 
		   return stack[--next]; // top item on stack
		else
		   return 0;
	}
	
	public int getStackSize() {
		return next;
	}
	
	private class StepThrough { 		
		// start stepping through at i=0
		private int i = 0; 
		
		// increment index
		public void increment() {
			if ( i < stack.length)
			   i++;
		}
		
		// retrieve current element
		public int current() {
			return stack[i];
		}
		
		// last element on stack?
		public boolean isLast(){
			if (i == getStackSize() - 1)
			   return true;
			else
			   return false;
		}
	}
	
	public StepThrough stepThrough() {
		return new StepThrough();
	}
	
	public static void main(String[] args) {
		
		// instantiate outer class as "stackOne"
		StackOfInts stackOne = new StackOfInts(15);
		
		// populate stackOne
		for (int j = 0 ; j < 15 ; j++) {
			stackOne.push(2*j);
		}
		
		// instantiate inner class as "iterator"
		StepThrough iterator = stackOne.stepThrough();
		
		// print out stackOne[i], one per line
		while(!iterator.isLast()) {
			System.out.print(iterator.current() + " ");
			iterator.increment();
		}
		System.out.println();
		
	}
	
}
