import java.util.Comparator;


public class AVLTree<D> {
	private AVLNode<D> root=null;
	private int nbitems;
	private Comparator<D> comparator;

	public AVLTree(Comparator<D> comparator){
		this.nbitems=0;
		this.comparator=comparator;
	}
	
	public AVLTree(Comparator<D> comparator,AVLNode<D> data){
		this.root=data;
		root.setRoot();
		this.nbitems=1;
		this.comparator=comparator;
	}
	
	public AVLNode<D> search(D data){
		AVLNode<D> currentNode = root;
		AVLNode<D> previousNode = root;
		while(currentNode!=null){
			if(comparator.compare(currentNode.getData(),data)<0){
					previousNode=currentNode;
					currentNode=currentNode.getRightNode();
			}else if(comparator.compare(currentNode.getData(),data)>0){
					previousNode=currentNode;
					currentNode=currentNode.getLeftNode();	
			}else{ //==0
				return currentNode;
			}
		}			
		return previousNode;
	}
	
	public boolean searchTest(D data){
		AVLNode<D> currentNode = root;
		while(currentNode!=null){
			if(comparator.compare(currentNode.getData(),data)<0){
					currentNode=currentNode.getRightNode();
			}else if(comparator.compare(currentNode.getData(),data)>0){
					currentNode=currentNode.getLeftNode();	
			}else{ //==0
				return true;
			}
		}			
		return false;
	}
	
	public boolean insert(D data){
		if(root==null){
			root=new AVLNode<D>(null, null, null, data);
			root.setRoot();
		}else{
			AVLNode<D> currentNode=search(data);
			if(comparator.compare(currentNode.getData(),data)<0){
				currentNode.setRightNode(new AVLNode<D>(currentNode, null, null, data));
			}else if(comparator.compare(currentNode.getData(),data)>0) {
				currentNode.setLeftNode(new AVLNode<D>(currentNode, null, null, data));	
			}else{
				return false;
			}
			updateHauteur(currentNode);
		}	
		nbitems++;
		return true;	
	}
	
	public boolean remove(D data){
		AVLNode<D> currentNode=search(data);
		if(comparator.compare(currentNode.getData(), data)!=0){ //si pas dans l'abre
			return false;
		}
		if(comparator.compare(currentNode.getData(), root.getData())==0){//si on supprime root
			if(currentNode.feuille()){
				root=null;
			}else if(currentNode.getLeftNode()==null){
				currentNode.setData(currentNode.getRightNode().getData());
				currentNode.setRightNode(null);
				updateHauteur(currentNode);
			}else if(currentNode.getRightNode()==null){
				currentNode.setData(currentNode.getLeftNode().getData());
				currentNode.setLeftNode(null);	
				updateHauteur(currentNode);
			}else{//2 childs
				AVLNode<D> node_to_move = searchLeftInRightSubTree(currentNode);
				currentNode.setData(node_to_move.getData());
				
				AVLNode<D> node_to_moveParent = node_to_move.getParent();
				if(comparator.compare(node_to_moveParent.getData(),currentNode.getData())==0){
					if(node_to_move.getRightNode()!=null){
						node_to_move.getRightNode().setParent(currentNode);
					}
					currentNode.setRightNode(node_to_move.getRightNode());
					updateHauteur(currentNode);
				}else{
					if(node_to_move.getRightNode()!=null){
						node_to_move.getRightNode().setParent(node_to_moveParent);
					}
					node_to_moveParent.setLeftNode(node_to_move.getRightNode());
					updateHauteur(node_to_moveParent);
				}
			}
		}else{ 
			AVLNode<D> parentNode = currentNode.getParent();
			if(currentNode.feuille()){
				if(parentNode.getLeftNode()!=null){
					if(comparator.compare(parentNode.getLeftNode().getData(),data)==0){
						parentNode.setLeftNode(null);
					}				
					else{
						parentNode.setRightNode(null);
					}
				}else{
					parentNode.setRightNode(null);
				}
				updateHauteur(parentNode);
			}else if(currentNode.getRightNode()==null){
					currentNode.getLeftNode().setParent(parentNode);
					if(comparator.compare(parentNode.getLeftNode().getData(),data)==0){
						parentNode.setLeftNode(currentNode.getLeftNode());
					}else{
						parentNode.setRightNode(currentNode.getLeftNode());
					}
					updateHauteur(parentNode);
			}else if(currentNode.getLeftNode()==null){
				currentNode.getRightNode().setParent(parentNode);
				if(comparator.compare(parentNode.getLeftNode().getData(),data)==0){
					parentNode.setLeftNode(currentNode.getRightNode());
				}else{
					parentNode.setRightNode(currentNode.getRightNode());
				}
				updateHauteur(parentNode);
			}else{//deux fils
				AVLNode<D> node_to_move = searchLeftInRightSubTree(currentNode);
				currentNode.setData(node_to_move.getData());
				
				AVLNode<D> node_to_moveParent = node_to_move.getParent();
				if(comparator.compare(node_to_moveParent.getData(),currentNode.getData())==0){
					if(node_to_move.getRightNode()!=null){
						node_to_move.getRightNode().setParent(currentNode);
					}
					currentNode.setRightNode(node_to_move.getRightNode());
					updateHauteur(currentNode);
				}else{
					if(node_to_move.getRightNode()!=null){
						node_to_move.getRightNode().setParent(node_to_moveParent);
					}
					node_to_moveParent.setLeftNode(node_to_move.getRightNode());
					updateHauteur(node_to_moveParent);
				}
			}
		}
		nbitems--;
		return true;
	}

	private AVLNode<D> searchLeftInRightSubTree(AVLNode<D> currentNode) {
		currentNode=currentNode.getRightNode();
		while(currentNode.getLeftNode()!=null){
			currentNode=currentNode.getLeftNode();
		}
		return currentNode;
	}

	public AVLNode<D> getRoot() {
		return root;
	}

	public int getNbitems() {
		return nbitems;
	}
	
	public int getTreeHauteur(){
		if(root==null){
			return 0;
		}
		return root.getHauteur();
	}
	
	public void show(AVLNode<D> node,int prof) {
		if(this.getTreeHauteur()== 0){
			System.out.println("arbre vide");
		}else{
			if(node!=null){
				for(int i=0;i< prof;i++){
					System.out.print(" ");
				}
				System.out.println(node.getData());
	
					show(node.getLeftNode(),prof+1);
					show(node.getRightNode(),prof+1);
				
			}
		}
	}

	public void updateHauteur(AVLNode<D> currentNode) {
		while(currentNode!=null){
			currentNode.setHauteur();
			int balance = currentNode.desequilibre();
			if(balance == -2){
				int balance_ssarbre_droit = currentNode.getRightNode().desequilibre();
				if(balance_ssarbre_droit == -1){
					rotationDroite(currentNode);
				}else if (balance_ssarbre_droit == 1){
					rotationDroiteGauche(currentNode);
				}
			}else if(balance == 2){
				int balance_ssarbre_gauche = currentNode.getLeftNode().desequilibre();
				if(balance_ssarbre_gauche == 1){
					rotationGauche(currentNode);
				}else if (balance_ssarbre_gauche == -1){
					rotationGaucheDroite(currentNode);
				}
			}
			currentNode=currentNode.getParent();
		}
	}

	private void rotationGaucheDroite(AVLNode<D> currentNode) {
		D r = currentNode.getData();
		AVLNode<D> t4 = currentNode.getRightNode();
		AVLNode<D> x = currentNode.getLeftNode();	
		AVLNode<D> t1 = x.getLeftNode();
		AVLNode<D> w = x.getRightNode();
		AVLNode<D> t2 = w.getLeftNode();
		AVLNode<D> t3 = w.getRightNode();

		currentNode.setData(w.getData());
		AVLNode<D> nx=new AVLNode<D>(currentNode,t1,t2,x.getData());
		if(t1!=null){ 
			t1.setParent(nx);
		}
		if(t2!=null){
			t2.setParent(nx);
		}
		currentNode.setLeftNode(nx);
		
		AVLNode<D> nr=new AVLNode<D>(currentNode,t3,t4,r);
		if(t3!=null){ 
			t3.setParent(nr);
		}
		if(t4!=null){
			t4.setParent(nr);
		}
		currentNode.setRightNode(nr);		
		
		updateHauteur(nx);
		updateHauteur(nr);
	}

	private void rotationDroite(AVLNode<D> currentNode) {
		D r = currentNode.getData();
		AVLNode<D> t1 = currentNode.getLeftNode();
		AVLNode<D> x = currentNode.getRightNode();	
		AVLNode<D> t2 = x.getLeftNode();
		AVLNode<D> t3 = x.getRightNode();
		
		currentNode.setData(x.getData());
		t3.setParent(currentNode);
		currentNode.setRightNode(t3);	

		AVLNode<D> nr=new AVLNode<D>(currentNode,t1,t2,r);
		if(t1!=null){
			t1.setParent(nr);
		}
		if(t2!=null){
			t2.setParent(nr);
		}
		currentNode.setLeftNode(nr);	
		
		updateHauteur(nr);
	}

	private void rotationDroiteGauche(AVLNode<D> currentNode) {
		D r = currentNode.getData();
		AVLNode<D> t1 = currentNode.getLeftNode();
		AVLNode<D> x = currentNode.getRightNode();	
		AVLNode<D> t4 = x.getRightNode();
		AVLNode<D> w = x.getLeftNode();
		AVLNode<D> t2 = w.getLeftNode();
		AVLNode<D> t3 = w.getRightNode();		
		
		currentNode.setData(w.getData());
		AVLNode<D> nr=new AVLNode<D>(currentNode,t1,t2,r);
		if(t1!=null){ 
			t1.setParent(nr);
		}
		if(t2!=null){
			t2.setParent(nr);
		}
		currentNode.setLeftNode(nr);	
		
		AVLNode<D> nx=new AVLNode<D>(currentNode,t3,t4,x.getData());
		if(t3!=null){ 
			t3.setParent(nx);
		}
		if(t4!=null){
			t4.setParent(nx);
		}
		currentNode.setRightNode(nx);
		
		updateHauteur(nr);
		updateHauteur(nx);
	}

	private void rotationGauche(AVLNode<D> currentNode) {
		D r = currentNode.getData();
		AVLNode<D> t3 = currentNode.getRightNode();
		AVLNode<D> x = currentNode.getLeftNode();	
		AVLNode<D> t1 = x.getLeftNode();
		AVLNode<D> t2 = x.getRightNode();
		
		currentNode.setData(x.getData());
		t1.setParent(currentNode);
		currentNode.setLeftNode(t1);
		
		AVLNode<D> nr=new AVLNode<D>(currentNode,t2,t3,r);
		if(t2!=null){
			t2.setParent(nr);
		}
		if(t3!=null){
			t3.setParent(nr);
		}
		currentNode.setRightNode(nr);	
		
		updateHauteur(nr);
	}

}
