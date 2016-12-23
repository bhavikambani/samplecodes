package com.sapient.usecases.uc15_CallCentreDevelopment;

public class ProjectManager implements IExecutive {

	IExecutive executive;
	volatile boolean isPMAvailable = true;

	public boolean attend() {
		if (isPMAvailable) {
			System.out.println("Call has taken up Manager");
			isPMAvailable = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isPMAvailable = true;
		} else {
			System.out.println("All executives are busy");
		}
		return isPMAvailable;
	}

	public void delegate(IExecutive executive) {
		this.executive = executive;
	}
}
