package viewpanels;

import java.time.LocalDate;

import models.Doctor;
import models.Models;
import modules.Modules;

public abstract class Panels
{
	public abstract void setDate(LocalDate date);
	public abstract void setFilter(int Filter);
	public abstract void setModels(Models model);
	public abstract void setModule(Modules module);
	public abstract void setDoctorFilter(Doctor filter);
	public abstract void refresh();
}
