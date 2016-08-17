package acc.controller;
import acc.model.Model;
import acc.view.View;

public interface Controller {
	void setModel(Model model);
	Model getModel();
	View getView();
	void setView(View view);
}