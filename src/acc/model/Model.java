package acc.model;

import acc.model.ModelEvent;

public interface Model {
	void notifyChanged(ModelEvent e);
}
