import java.util.InputMismatchException;
import java.util.Scanner;


public class Test {
	public static void main(String[] args){
		AVLTree<Number> tree= new AVLTree<Number>(new NumberComparator());
		tree.show(tree.getRoot(),tree.getTreeHauteur());
		System.out.println("hauteur de l'arbre "+tree.getTreeHauteur());
		boolean fin = false;
				String operation;
				Scanner scanner = new Scanner (System.in);
				while(!fin){
					System.out.print("Entrez l'operation a effectuer sur la SkipList,\n I:inserer,R:supprimer,S:recherche, X:fin du programme ");
					operation = scanner.next();					
					
					int value;
					boolean retour_operation;
					switch(operation.toLowerCase().charAt(0)){
						case 'i':
							System.out.println ("	-> Operation : Insertion\n");	
							System.out.print("Entrez l'entier a inserer dans l'AVL :");
							value = scanner.nextInt();
							retour_operation = tree.insert(value);
							System.out.println("\nEtat de la l'AVL :");						
							tree.show(tree.getRoot(),tree.getTreeHauteur());
							if(retour_operation){
								System.out.println("\nInsertion effectuee avec succes\n");
							}else{
								System.out.println("\nCle deja presente\n");
							}
						break;
						case 's':
							System.out.println ("	-> Operation : Suppression\n\nEtat de l'AVL :");	
							System.out.print("Entrez l'entier a supprimer dans l'AVL :");
							value = scanner.nextInt();
							retour_operation = tree.remove(value);
							System.out.println("\nEtat de l'AVL :");
							tree.show(tree.getRoot(),tree.getTreeHauteur());
							if(retour_operation){
								System.out.println("\nSuppression effectuee avec succes\n");
							}else{
								System.out.println("\nCle non presente\n");
							}
						break;
						case 'r':
							System.out.println ("	-> Operation : Recherche\n\nEtat de l'AVL :");
							System.out.print("Entrez l'entier a rechercher dans l'AVL :");
							value = scanner.nextInt();
							retour_operation = tree.searchTest(value);
							System.out.println("\nEtat de l'AVL :");
							tree.show(tree.getRoot(),tree.getTreeHauteur());
							if(retour_operation){
								System.out.println("\nRecherche effectuee avec succes: Cle = "+value+"\n");
							}else{
								System.out.println("\nElement non present\n");
							}
						break;
						case 'x':
							fin=true;
						break;
						default: throw new InputMismatchException("operateur invalide");
					}
				}
	}
}
