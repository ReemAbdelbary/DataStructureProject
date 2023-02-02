package Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Data  {
	
	public final static int NUMBER_CHAR = 256;
	public static int next = -1;
	public static TreeNode huffmanRoot;
	
	//main for testing   
	
	  public static void main(String[] args) {
		
	       ArrayList<String> Lines = ReadXML("D:\\Reem\\data structure\\project\\sample.xml");
	       ArrayList<String> LinesNoSpace = spaceRemover(Lines);  
	       String minified = XMLminify(LinesNoSpace);
	       String clear = minified.substring(4); 
	       StringBuilder g = encode(clear);
	       String nn = g.toString();
	
	try {
		Compress(nn,"D:\\Reem\\data structure\\test project\\compressednew");
	} catch (IOException e) {
		
		e.printStackTrace();
	}

	System.out.println(g);
	
	StringBuilder n = decode(g,huffmanRoot);
	System.out.println(n);

	  }

	  public static void fill_Table(TreeNode root, String[] replacementTable, String code) {
			
			if (root instanceof Leaf) {
				replacementTable[((Leaf) root).getCharacter()] = code;
				return;
			}
			// left = 0 , right = 1
			fill_Table(root.leftChild, replacementTable, code + "0");
			fill_Table(root.rightChild, replacementTable, code + "1");
		}

	  public static StringBuilder charToBinaryString(char c) {
			StringBuilder result = new StringBuilder();
			String binary = Integer.toBinaryString(c & 0xFF);
			for(int i = 0 ; i < (8 - binary.length()) ; i++) {
				result.append('0');
			}
			result.append(binary);
			return result;
		}
		
	  public static void HuffmanTree(TreeNode root, StringBuilder s) {
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

	  public static StringBuilder binarfyHuffmanTree(TreeNode huffmanRoot) {
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

	  public static StringBuilder encode(String s) {
				int[] freqArray = new int[NUMBER_CHAR];
				String[] replacementTable = new String[NUMBER_CHAR];
				PriorityQueue<TreeNode> nodes = new PriorityQueue<TreeNode>();
				StringBuilder encoded = new StringBuilder();
				for (int i = 0; i < s.length(); i++) {
					freqArray[s.charAt(i)]++;
				}
				// to fill heap
				for (int i = 0; i < NUMBER_CHAR; i++) {
					if (freqArray[i] != 0) {
						nodes.add(new Leaf((char) i, freqArray[i]));
					}
				}
				while (nodes.size() != 1) {
					
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

	  public static StringBuilder decode(StringBuilder encoded, TreeNode huffmanRoot) {
				StringBuilder decoded = new StringBuilder();
				TreeNode start = huffmanRoot;
				for (int i = 0; i < encoded.length(); i++) {
					// rules for traversing the tree
					if (encoded.charAt(i) == '0') {
						start = start.leftChild;
					} else if (encoded.charAt(i) == '1') {
						start = start.rightChild;
					}
					
					if (start instanceof Leaf) {
						decoded.append(((Leaf) start).getCharacter());
						start = huffmanRoot;
					}
				}
				return decoded;
			}
			// to save as compressed file

		    private static void Compress(String binary, String filePath) throws IOException {
		    	
		    	FileOutputStream fileOS = new FileOutputStream(filePath);
				DataOutputStream dataOS = new DataOutputStream(fileOS);
				byte flag = 0;
				byte toByte = 0;
				byte count = 7;
				byte remainder = (byte) (binary.length() % 8);
				if (remainder != 0) {
					flag = (byte) (8 - remainder);
				}
				dataOS.writeByte(flag);
				for (int i = 0; i < binary.length(); i++) {
					if (binary.charAt(i) == '1') {
						toByte |= (1 << count);
					}
					//  8 bits written as byte
					if (count == 0) {
						dataOS.writeByte(toByte);
						toByte = 0;
						count = 8;
					}
					count--;
				}
				dataOS.writeByte(toByte);
				dataOS.close();
			}
}
class TreeNode implements Comparable<TreeNode> {

	// for huffman
	protected int frequency;
	protected TreeNode leftChild;
	protected TreeNode rightChild;

	public TreeNode(TreeNode leftChild, TreeNode rightChild) {
		frequency = leftChild.frequency + rightChild.frequency;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
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

}

