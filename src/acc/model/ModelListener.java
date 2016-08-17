package acc.model;

import acc.model.ModelEvent;

public interface ModelListener {
	public void modelChanged(ModelEvent event);
}
