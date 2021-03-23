public class WeightedCloud extends Cloud {	
	private double points[][];
	
	@Override	
	public double[] findCenterOfMass() {
		points=getPoints();
		
		double[] cm = new double[2];
		double sumx=0, sumy=0, sumw=0;
	
		for(int i=0; i<points.length; i++) {
			sumx+=(points[i][0]*points[i][2]);
			sumy+=(points[i][1]*points[i][2]);
			sumw+=points[i][2];
		}
		
		if(sumw==0) {
			cm[0]=0;
			cm[1]=0;
		}
		else {
			cm[0]=sumx/sumw;
			cm[1]=sumy/sumw;
		}
	
		return cm;
	}
	
	public void setCloudRandomly(int sizeOfCloud) {
		points = new double[sizeOfCloud][3];
		
		for (int i = 0; i < sizeOfCloud; i++) {
			points[i][0] = 100 * Math.random();
			points[i][1] = 100 * Math.random();
			points[i][2] = 100 * Math.random();
		}
	}
	
	public double[][] getPoints() {
		return points;
	}
  
	public double[] getSinglePoint(int i) {
		return points[i];
	}
}