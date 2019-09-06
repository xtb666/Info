package com.togest.domain;

public class Point5CDTO extends Point5C<Point5CDTO> {

	 	private String lineName;

	   // private String sectionId;

	    private String stationName;

	    private String installPillarName;
	    
	    private String trackName;
	    
	    private String directionName;

		public String getLineName() {
			return lineName;
		}

		public void setLineName(String lineName) {
			this.lineName = lineName;
		}

		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		public String getInstallPillarName() {
			return installPillarName;
		}

		public void setInstallPillarName(String installPillarName) {
			this.installPillarName = installPillarName;
		}

		public String getTrackName() {
			return trackName;
		}

		public void setTrackName(String trackName) {
			this.trackName = trackName;
		}

		public String getDirectionName() {
			return directionName;
		}

		public void setDirectionName(String directionName) {
			this.directionName = directionName;
		}
	    
	    
}
