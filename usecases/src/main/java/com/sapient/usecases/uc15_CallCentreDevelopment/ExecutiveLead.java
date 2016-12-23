package com.sapient.usecases.uc15_CallCentreDevelopment;

public class ExecutiveLead implements IExecutive {

	IExecutive executive;

	volatile boolean isLeadAvailable = true;

	public boolean attend() {
		if (isLeadAvailable) {
			System.out.println("Call has taken up by Lead");
			isLeadAvailable = false;
			try {
				Thread.sleep(1000);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isLeadAvailable = true;
		} else {
			System.out.println("delegated to PM");
			return executive.attend();

		}
		return isLeadAvailable;
	}

	public void delegate(IExecutive executive) {
		this.executive = executive;
	}

}
