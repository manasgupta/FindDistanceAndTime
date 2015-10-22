package com.exlservices.DistanceFinder.model;

/**
 * @author Manas Gupta
 * @since 21st Oct, 2015
 * @version 1.0
 */
public class DrivingDistanceAndTime {

	private String sourceAddress;

	private String destinationAddress;

	private String drivingDistance;

	private String drivingTime;

	public DrivingDistanceAndTime() {
		super();
	}

	public DrivingDistanceAndTime(String sourceAddress,
			String destinationAddress, String drivingDistance,
			String drivingTime) {
		super();
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAddress;
		this.drivingDistance = drivingDistance;
		this.drivingTime = drivingTime;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDrivingDistance() {
		return drivingDistance;
	}

	public void setDrivingDistance(String drivingDistance) {
		this.drivingDistance = drivingDistance;
	}

	public String getDrivingTime() {
		return drivingTime;
	}

	public void setDrivingTime(String drivingTime) {
		this.drivingTime = drivingTime;
	}

	@Override
	public String toString() {
		return "DrivingDistanceAndTime [SourceAddress=" + getSourceAddress()
				+ ", DestinationAddress=" + getDestinationAddress()
				+ ", DrivingDistance=" + getDrivingDistance()
				+ ", DrivingTime=" + getDrivingTime() + "]";
	}

}
