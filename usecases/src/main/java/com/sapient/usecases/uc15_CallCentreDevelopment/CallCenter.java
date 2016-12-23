package com.sapient.usecases.uc15_CallCentreDevelopment;

public class CallCenter implements Runnable {

	IExecutive fresher, lead, pm;

	public CallCenter(IExecutive fresher, IExecutive lead, IExecutive pm) {
		this.fresher = fresher;
		this.lead = lead;
		this.pm = pm;
		this.fresher.delegate(lead);
		this.lead.delegate(pm);
	}

	public static void main(String[] args) {

		IExecutive fresher = new ExecutiveFresher();
		IExecutive lead = new ExecutiveLead();
		IExecutive pm = new ProjectManager();
		CallCenter callCenter = new CallCenter(fresher, lead, pm);
		Thread[] t = new Thread[15];
		for (int i = 0; i < 15; i++) {
			System.out.println("Thread-" + i);
			t[i] = new Thread(callCenter);
			t[i].start();
		}
	}

	public void run() {
		if (!fresher.attend()) {
			run();
		}
	}

}
