
public class AVLNode<D> {
	private AVLNode<D> leftChild=null;
	private AVLNode<D> rightChild=null;
	private AVLNode<D> parent=null;
	private int hauteur=0;
	private D data=null;
	
	public AVLNode(AVLNode<D> parent,AVLNode<D> left,AVLNode<D> right,D data){
		this.parent = parent;
		this.leftChild = left;
		this.rightChild = right;
		this.data = data;
		this.hauteur = 1;
	}
	
	public AVLNode(D data){
		this.data = data;
		this.hauteur = 1;
	}
	

	public AVLNode<D> getLeftNode() {
		return leftChild;
	}

	public void setLeftNode(AVLNode<D> leftChild) {
		this.leftChild = leftChild;
	}

	public AVLNode<D> getRightNode() {
		return rightChild;
	}

	public void setRightNode(AVLNode<D> rightChild) {
		this.rightChild = rightChild;
	}

	public D getData() {
		return data;
	}

	public boolean feuille(){
		return (this.getLeftNode()==null && this.getRightNode()==null);
	}
	
	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur() {
		if(this.feuille()){
			this.hauteur=1;
		}else{
			int hauteurGauche = 0,hauteurDroite=0;
			if(this.getLeftNode()!=null){
				hauteurGauche=this.getLeftNode().getHauteur();
			}
			if(this.getRightNode()!=null){
				hauteurDroite=this.getRightNode().getHauteur();
			}
			this.hauteur = (1 + Math.max(hauteurGauche,hauteurDroite));
		}
	}

	public void setData(D data) {
		this.data = data;
	}

	public void increaseHauteur() {
		this.hauteur++;
	}

	public void decreaseHauteur() {
		this.hauteur--;
	}

	public AVLNode<D> getParent() {
		return parent;
	}

	public void setParent(AVLNode<D> parent) {
		this.parent = parent;
	}	
	
	public int desequilibre() {
		if(this.feuille()){
			return 0;
		}else{
			int hauteurGauche = 0,hauteurDroite=0;
			if(this.getLeftNode()!=null){
				hauteurGauche=this.getLeftNode().getHauteur();
			}
			if(this.getRightNode()!=null){
				hauteurDroite=this.getRightNode().getHauteur();
			}
			return (hauteurGauche-hauteurDroite);
		}
	}

	public void setRoot() {
		this.hauteur = 1;
	}
}
