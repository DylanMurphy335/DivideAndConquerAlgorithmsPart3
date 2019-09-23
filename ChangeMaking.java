package Part3;

public class ChangeMaking {

	public ChangeMaking(){}

	
	public int selectionFunctionFirstCandidate(MyList<Integer> candidates) {
		if (candidates.length() > 0) { return 0; } 
		else { return -1; }
	}

	
	public int selectionFunctionBestCandidate( MyList<Integer> candidates ){
		int m = Integer.MIN_VALUE, index = 0;
		for (int i = 0; i < candidates.length(); i++) {
			if (m < candidates.getElement(i)) {
				m = candidates.getElement(i);
				index = i;
			}
		}
		return index;
	}
		

	public boolean feasibilityTest(int candidateValue, int amount, int changeGenerated){
		if (candidateValue <= (amount-changeGenerated)) { return true; }
		else { return false; }
	}
	
	
	public boolean solutionTest(MyList<Integer> candidates) {
		if (candidates.length() == 0) { return true; }
		else { return false; }
	}

	
	public int  objectiveFunction(MyList<Integer> sol){
		int objFun =0;
		for (int i = 0; i < sol.length(); i++) { objFun = objFun + sol.getElement(i); }
		return objFun;
	}
	
	
	public MyList<Integer> solve(int typeSelectFunc, MyList<Integer> coinValues, int amount){
		MyList<Integer> res = null, candidates = null, candidatesValues = null, resValues = null;
		int solutionValue = 0, capacityUsed = 0;
		res = new MyDynamicList<Integer>();
		candidates = new MyDynamicList<Integer>();
		
		for (int i = 0; i < coinValues.length(); i++) { candidates.addElement(i, coinValues.getElement(i)); }
		while (solutionTest(candidates) == false) {
			int candidateSelected = -1;
			switch (typeSelectFunc) {
			case 1:
				candidateSelected = selectionFunctionFirstCandidate(candidates);
				break;
			case 2:
				candidateSelected = selectionFunctionBestCandidate(candidates);
				break;
			}

			int candidateSelectedWeight = candidates.getElement(candidateSelected);
			if (feasibilityTest(candidateSelectedWeight, amount, capacityUsed) == true) {
				res.addElement(res.length(), candidateSelectedWeight);
				if(resValues!=null && candidatesValues!=null) { resValues.addElement(resValues.length(), candidatesValues.getElement(candidateSelected)); }
				capacityUsed = capacityUsed + candidateSelectedWeight;
			}
			if (feasibilityTest(candidateSelectedWeight, amount, capacityUsed) == false) { candidates.removeElement(candidateSelected); }
		}
		
		solutionValue = objectiveFunction(res);
		System.out.print("Solution: ");
		for (int i = 0; i < res.length(); i++) {
			System.out.print(res.getElement(i) + ", ");
		}
		System.out.println("\nObjective Function Value= " + solutionValue);
		System.out.println("Accuracy: " + (amount-capacityUsed));
		return res;
	}
}
