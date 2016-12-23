package com.sapient.usecases.uc15_CallCentreDevelopment;

public class ExecutiveFresher implements IExecutive {

	IExecutive executive;
	volatile boolean isAvailable = true;

	public boolean attend() {
		if (isAvailable) {
			System.out.println("Call has taken up by Fresher");
			isAvailable = false;
			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isAvailable = true;
		} else {
			System.out.println("delegated to Lead");
			return executive.attend();
		}
		return isAvailable;
	}

	public void delegate(IExecutive executive) {
		this.executive = executive;
	}

}
