package Helpers;
import DataStructure.Vertex;

public final class GeographicDistances {
	
	private GeographicDistances() {
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	public static double distance(Vertex v1, Vertex v2){
		double lat1 = v1.getLat();
		double lon1 = v1.getLng();
		double lat2 = v2.getLat();
		double lon2 = v2.getLng();
		
		double theta = lon1 - lon2;
		double dist = (Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta)));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		//Dist is given in miles
		dist = (dist * 60.0 * 1.1515);
		//Convert to meters
		dist = (dist * 1.609344*1000.0);
		return dist;
	}
}
