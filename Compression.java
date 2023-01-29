package Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Data  {
	
	private final static int NUMBER_CHAR = 256;
	private static int next = -1;
	private static TreeNode huffmanRoot;
	
	  public static void main(String[] args) {
		
		  ArrayList<String> Lines = ReadXML("D:\\Reem\\data structure\\project\\sample.xml");

	        ArrayList<String> LinesNoSpace = spaceRemover(Lines);  
	      String minified = XMLminify(LinesNoSpace);
	      String clear = minified.substring(4); 
	      
	      System.out.println(clear);
	  // XMLcompression (clear);
		  
	
	StringBuilder g = encode(clear);
	System.out.println(g);
	StringBuilder n = decode(g,huffmanRoot);
	System.out.println(n);
	
	  

	  }

	  private static void fill_Table(TreeNode root, String[] replacementTable, String code) {
			// fill the hashtable which contains each character and its replacement
			if (root instanceof Leaf) {
				replacementTable[((Leaf) root).getCharacter()] = code;
				return;
			}
			// left = 0 , right = 1
			fill_Table(root.leftChild, replacementTable, code + "0");
			fill_Table(root.rightChild, replacementTable, code + "1");
		}

		private static StringBuilder charToBinaryString(char c) {
			StringBuilder result = new StringBuilder();
			String binary = Integer.toBinaryString(c & 0xFF);
			for(int i = 0 ; i < (8 - binary.length()) ; i++) {
				result.append('0');
			}
			result.append(binary);
			return result;
		}
		private static void HuffmanTree(TreeNode root, StringBuilder s) {
			if (root instanceof Leaf) {
				s.append('0');
				s.append(((Leaf) root).getCharacter());
				return;
			} else {
				s.append('1');
				HuffmanTree(root.leftChild, s);
				HuffmanTree(root.rightChild, s);
			}
		}

		private static StringBuilder binarfyHuffmanTree(TreeNode huffmanRoot) {
			StringBuilder sb = new StringBuilder();
			StringBuilder temp = new StringBuilder();
			HuffmanTree(huffmanRoot, sb);
			char current;
			for (int i = 0; i < sb.length() - 1; i++) {
				current = sb.charAt(i);
				if (current == '1') {
					temp.append(current);

				} else {
					temp.append(current);
					temp.append(charToBinaryString(sb.charAt(++i)));
				}
			}
			return temp;
		}

		private static TreeNode decodeHuffmanTree(String encoded) {
			// next should start from -1;
			TreeNode decodedRoot;
			next += 1;
			// a leaf
			if (encoded.charAt(next) == '0') {
				decodedRoot = new Leaf();
				char currentChar = 0;
				char count = 7;
				for (int i = next + 1; i < next + 9; i++) {
					if (encoded.charAt(i) == '1') {
						currentChar |= (1 << count);
					}
					count--;
				}
				((Leaf) decodedRoot).setCharacter(currentChar);
				next = next + 8;
			} else {
				decodedRoot = new TreeNode();
				decodedRoot.leftChild = decodeHuffmanTree(encoded);
				decodedRoot.rightChild = decodeHuffmanTree(encoded);
			}

			return decodedRoot;
		}
		
			private static StringBuilder encode(String s) {
				int[] freqArray = new int[NUMBER_CHAR];
				String[] replacementTable = new String[NUMBER_CHAR];
				PriorityQueue<TreeNode> nodes = new PriorityQueue<TreeNode>();
				StringBuilder encoded = new StringBuilder();
				for (int i = 0; i < s.length(); i++) {
					freqArray[s.charAt(i)]++;
				}
				// fill the heap
				for (int i = 0; i < NUMBER_CHAR; i++) {
					if (freqArray[i] != 0) {
						nodes.add(new Leaf((char) i, freqArray[i]));
					}
				}
				while (nodes.size() != 1) {
					// remove the two smallest elements in the heap, combine them into a parent node and re-insert it in the heap
					TreeNode combined = new TreeNode(nodes.poll(), nodes.poll());
					nodes.add(combined);
				}
				huffmanRoot = nodes.poll();
				fill_Table(huffmanRoot, replacementTable, "");

				for (int i = 0; i < s.length(); i++) {
					encoded.append(replacementTable[s.charAt(i)]);
				}
				return encoded;
			}

			private static StringBuilder decode(StringBuilder encoded, TreeNode huffmanRoot) {
				StringBuilder decoded = new StringBuilder();
				TreeNode start = huffmanRoot;
				for (int i = 0; i < encoded.length(); i++) {
					// rules for traversing the tree
					if (encoded.charAt(i) == '0') {
						start = start.leftChild;
					} else if (encoded.charAt(i) == '1') {
						start = start.rightChild;
					}
					// if you hit a leaf node just add its value and restart the tree
					if (start instanceof Leaf) {
						decoded.append(((Leaf) start).getCharacter());
						start = huffmanRoot;
					}
				}
				return decoded;
			}

			
			public static ArrayList<String> ReadXML(String path){
		         try {
					BufferedReader reader = new BufferedReader(new FileReader(path));
				        
		                                String line;
		                                ArrayList<String> LinesArray = new ArrayList<String>();
		                                while((line=reader.readLine()) != null){
		                                    LinesArray.add(line);
		                                }
		                                reader.close();
		                                return LinesArray;
		                       
			} 
		      catch(IOException c){
		          c.printStackTrace();
		      }
		         return null;
		    }
		    
		    public static ArrayList<String> spaceRemover(ArrayList<String> data){
		        ArrayList<String> result = new ArrayList<String>();
		        ArrayList<String> finalresult = new ArrayList<String>();
		        for(String d : data){
		            char[] lineChars = d.toCharArray();
		            boolean isTag = false;
		            int start = 0;
		            int end = lineChars.length - 1;
		            for(int x=0; x<lineChars.length; x++){
		                if(lineChars[x] == '<'){
		                    isTag = true;
		                    break;
		                }
		            }
		            if(isTag){
		                while(lineChars[start] != '<' ){
		                    if(lineChars[start] == ' '){
		                        lineChars[start] = '\0'; 
		                    }
		                    start++;   
		                }
		                while(lineChars[end] != '>' ){
		                    if(lineChars[end] == ' '){
		                       lineChars[end] = '\0'; 
		                    }
		                    end--;
		                
		                }   
		            }
		            else{
		                while(lineChars[start] ==  ' ' ){
		                     if(lineChars[start] == ' '){
		                        lineChars[start] = '\0'; 
		                    }
		                    start++;   
		                }
		                while(lineChars[end] ==  ' ' ){
		                     if(lineChars[end] == ' '){
		                        lineChars[end] = '\0'; 
		                    }
		                    end--;   
		                }
		            }
		           d = String.valueOf(lineChars);
		           result.add(d);
		        }
		        
		        for(String r : result){
		            char[] lineChars = r.toCharArray();
		            int sizee = lineChars.length;
		            for(int x=0; x<lineChars.length; x++){
		                if(lineChars[x] == '\0'){
		                    sizee--;
		                }
		            }
		            char[] finallineChars = new char[sizee];
		            int finalx = 0;
		            for(int x=0; x<lineChars.length; x++){
		                if(lineChars[x] == '\0'){
		                    continue;
		                }
		                finallineChars[finalx] = lineChars[x];
		                finalx++;
		            }
		            
		           r = String.valueOf(finallineChars);
		           finalresult.add(r);
		        }
		        return finalresult;
		    }
		    
		    
		    public static String XMLminify (ArrayList<String> noSpace){
		        String minifyResult = null;
		        for(String n : noSpace){
		          char[] noSpaceChars = n.toCharArray(); 
		          int y ;
		          for(y=0; y<noSpaceChars.length; y++){
		              if(noSpaceChars[y] == '\n'){
		                  noSpaceChars[y] = '\0';
		              }
		          }
		          n = String.valueOf(noSpaceChars);
		          minifyResult += n;
		        }
		        
		        return minifyResult;
		    }
		    		
			
		
}

class TreeNode implements Comparable<TreeNode> {
	protected int frequency;
	protected TreeNode leftChild;
	protected TreeNode rightChild;

	// Creates a node from the combined frequencies of its children
	public TreeNode(TreeNode leftChild, TreeNode rightChild) {
		frequency = leftChild.frequency + rightChild.frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	// this will be used in the child class leaf
	public TreeNode(int freq) {
		frequency = freq;
		leftChild = null;
		rightChild = null;
	}

	public TreeNode() {
		leftChild = null;
		rightChild = null;
		frequency = 0;
	}

	// in-order tree traversal
	private void print(TreeNode root) {
		if (root instanceof Leaf) {
			System.out.println(root);
			return;
		}
		print(root.leftChild);
		print(root.rightChild);
	}

	public void print() {
		print(this);
	}

	// this will be used later for sorting
	@Override
	public int compareTo(TreeNode nodeToCompare) {
		if (frequency == nodeToCompare.frequency) {
			return 0;
		} else if (frequency > nodeToCompare.frequency) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "" + frequency;
	}
}

class Leaf extends TreeNode {
	private char character;

	public Leaf(char character, int frequency) {
		super(frequency);
		this.character = character;
	}

	public Leaf() {
		super();
		character = ' ';
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char c) {
		character = c;
	}

	@Override
	public String toString() {
		return character + " : " + frequency;
	}
}
