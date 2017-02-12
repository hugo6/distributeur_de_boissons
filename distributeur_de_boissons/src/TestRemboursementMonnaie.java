import java.util.Scanner;

/**
 * Classe de test de l'algorithme de remboursement d'un distributeur de boissons
 * Le remboursement privil�gie les pi�ces les plus petites en premier
 * @author hugo Vaillant
 *
 */
public class TestRemboursementMonnaie {
	/**
	 * M�thode main
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choixBoisson, montantInsere, prixCentimeBoisson, montantCentimeARembourser, sommeMonnaieMachine;
		Boisson boissonChoisie;
		int montantCentimeCagnotte=0;
		int memoirePiece=0;
		
		//initialisation du tableau de pi�ces de la machine (ex 200centimes = 2�)
		int[][] tabPiecesMachine = {
				{1,99},
				{2,100},
				{5,100},
				{10,100},
				{20,100},
				{50,100},
				{100,100},
				{200,100},
		};
		
		//initialisation du tableau de pi�ces tampon
		int[][] tabPiecesBuffer = {
				{1,0},
				{2,0},
				{5,0},
				{10,0},
				{20,0},
				{50,0},
				{100,0},
				{200,0},
		};
		
		Boisson b1 = new Boisson("Red-Bull", 150);
		Boisson b2 = new Boisson("Caf�", 40);
		Boisson b3 = new Boisson("Eau-plate", 100);
		
		System.out.println("Quelle boisson voulez-vous ?");
		System.out.println("1 - Red-Bull (1,5�)");
		System.out.println("2 - Caf� (0,40�)");
		System.out.println("3 - Eau-plate (1�)");
		choixBoisson = sc.nextInt();
		
		System.out.println("Indiquez le montant ins�r�e (en centimes) :");
		montantInsere = sc.nextInt();
		sc.close();
		
		if(choixBoisson == 1) {
			boissonChoisie = b1;
		}else if (choixBoisson == 2) {
			boissonChoisie = b2;
		}else if (choixBoisson == 3) {
			boissonChoisie = b3;
		}else {
			boissonChoisie = b1;
		}
		prixCentimeBoisson = boissonChoisie.getPrix();
		montantCentimeARembourser = montantInsere - prixCentimeBoisson;		
		sommeMonnaieMachine = calculSommeMonnaieMachine(tabPiecesMachine);
		
		if(montantCentimeARembourser > sommeMonnaieMachine){
			System.out.println("La machine n'as pas assez d'argent pour vous rembourser : annulation de la transaction");
			System.exit(1);
		}else if(montantCentimeARembourser == 0){
			System.out.println("Compte rond : pas de monnaie � rembourser");
			System.exit(1);
		}else if(montantCentimeARembourser < 0){
			System.out.println("Vous n'avez pas assez d'argent !");
			System.exit(1);
		}
		calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesBuffer, memoirePiece, montantCentimeCagnotte, montantCentimeARembourser);
	}
	
	/**
	 * Calcul le remboursement avec les plus petites pi�ces en premier
	 * @param tabPiecesMachine : tableau des pi�eces de la machine
	 * @param tabPiecesCagnotte : tableau des pi�ces � rembourser au client
	 * @param niveauDePiece : niveau de pi�ce dans les tableau (ex : niveau 1 pour 0,02�)
	 * @param montantCagnotte : montant effectif du remboursement au client
	 * @param montantARembourser : montant de remboursement pr�vu pour le client
	 */
	private static void calculRemboursementPetitesPieces(int[][] tabPiecesMachine, int[][] tabPiecesCagnotte, int niveauDePiece, int montantCagnotte, int montantARembourser){
		if(montantCagnotte > montantARembourser){
			if(tabPiecesCagnotte[niveauDePiece][1] > 0){//reste des pi�ces dans cette cat�gorie de pi�ces
				if(tabPiecesCagnotte[niveauDePiece][0] <= (montantCagnotte-montantARembourser)){//la valeur de la pi�ce n'est pas trop grosse
					tabPiecesCagnotte[niveauDePiece][1]--;
					tabPiecesMachine[niveauDePiece][1]++;
					montantCagnotte-=tabPiecesCagnotte[niveauDePiece][0];
					calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesCagnotte, niveauDePiece, montantCagnotte, montantARembourser);
				}else{//valeur de la pi�ce trop grosse
					niveauDePiece--;
					calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesCagnotte, niveauDePiece, montantCagnotte, montantARembourser);
				}
			}else{//plus de pi�ces dans cette categ
				niveauDePiece--;
				calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesCagnotte, niveauDePiece, montantCagnotte, montantARembourser);
			}
		}else if(montantCagnotte == montantARembourser){
			System.out.println("REMBOURSEMENT");
			System.out.println("montant du remboursement :" + montantCagnotte);
			afficherPieces(tabPiecesCagnotte);
			System.exit(0);
		}else{
			//cagnotte pas pleine
			if(tabPiecesMachine[niveauDePiece][1] > 0){//si il reste des pi�ces de la cat�gorie dans la machine
				tabPiecesMachine[niveauDePiece][1]--;//on l'enl�ve de la machine
				tabPiecesCagnotte[niveauDePiece][1]++;//on rajoute une piece de la categ dans le tab cagnotte
				montantCagnotte+=tabPiecesMachine[niveauDePiece][0];
				calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesCagnotte, niveauDePiece, montantCagnotte, montantARembourser);//RECURSION
			}else{//plus de pi�ces d'une categorie
				niveauDePiece++;
				calculRemboursementPetitesPieces(tabPiecesMachine, tabPiecesCagnotte, niveauDePiece, montantCagnotte, montantARembourser);//RECURSION
			}
		}
		
	}

	/**
	 * Affiche les pi�ces d'un tableau
	 * @param tabPieces : tableau de pi�ces � afficher
	 */
	private static void afficherPieces(int[][] tabPieces) {
		float valeurPieceEuros;
		for (int i = 0; i < tabPieces.length; i++) {
			valeurPieceEuros = (float) tabPieces[i][0]/100;
			System.out.println(tabPieces[i][1] + " Pi�ce(s) de "+ valeurPieceEuros + "�");
		}
	}

	/**
	 * Calcul de la valeur de la somme des pi�ces d'un tableau
	 * @param tabPieces : tableau de pi�ces
	 * @return montant de la somme des pi�ces du tableau
	 */
	private static int calculSommeMonnaieMachine(int[][] tabPieces) {
		int montant=0;
		for (int i = 0; i < tabPieces.length; i++) {
			montant+=(tabPieces[i][0]*tabPieces[i][1]);
		}
		return montant;
	}
	
}