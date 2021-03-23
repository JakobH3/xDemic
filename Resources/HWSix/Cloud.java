public class Cloud {

  private double[][] points;

  public String getStudentInfo() {
    return "TJ Scherer CS 225, Section 01\n\n";
  }
  
  public int[] findClosestPoints() {
	int[] closestPair = new int[2];
	double d=0, min=Double.MAX_VALUE;
	
	for(int i=0; i<points.length; i++) {
		for(int j=0; j<points.length; j++) {
			d=distance(points[i][0], points[i][1], points[j][0], points[j][1]);
			if(j!=i) {
				if(d<min) {
					min=d;
					closestPair[0]=i;
					closestPair[1]=j;
				}
			}
		}
	}
	
    return closestPair;
  }
  
  public int[] findFurthestPoints() {
	int[] furthestPair = new int[2];
	double d=0, max=-1;
    
	for(int i=0; i<points.length; i++) {
		for(int j=0; j<points.length; j++) {
			if(j!=i) {
				d=distance(points[i][0], points[i][1], points[j][0], points[j][1]);
				if(d>max) {
					max=d;
					furthestPair[0]=i;
					furthestPair[1]=j;
				}
			}
		}
	}
	
	return furthestPair;
  }
  
  public double[] findCenterOfMass() {
	double[] cm = new double[2];
	double sumx=0, sumy=0;
	
	for(int i=0; i<points.length; i++) {
		sumx+=points[i][0];
		sumy+=points[i][1];
	}
	cm[0]=sumx/points.length;
	cm[1]=sumy/points.length;
	
    return cm;
  }
  
  public int findClosestToCenter() {
	int closestPoint = 0;
	double[] cm = new double[2];
	double min=Double.MAX_VALUE, d=0;
	
	cm=findCenterOfMass();
	
	for(int i=0; i<points.length; i++) {
		d=distance(points[i][0], points[i][1], cm[0], cm[1]);
		if(d<min) {
			min=d;
			closestPoint=i;
		}
	}
	
    return closestPoint;
  }
  
  public double distance(double px, double py, double qx, double qy) {
	   return Math.sqrt(((px-qx)*(px-qx))+((py-qy)*(py-qy)));
  }

	/*****SETTERS*****/
  public void setCloudRandomly(int sizeOfCloud) {
    points = new double[sizeOfCloud][2];
	
	for (int i = 0; i < sizeOfCloud; i++) {
		points[i][0] = 100 * Math.random();
		points[i][1] = 100 * Math.random();
	}
  }
  
  public void setCloud(double[][] newPoints) {
	  points=newPoints;
  }

	/*****GETTERS*****/
  public double[][] getPoints() {
	  return points;
  }
  
  public double[] getSinglePoint(int i) {
	  return points[i];
  }
}