package com.sapient.usecases.uc15_CallCentreDevelopment;

public interface IExecutive {
	boolean attend();

	void delegate(IExecutive executive);

}
