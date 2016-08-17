package acc.controller;

import acc.model.AgentModel;
import acc.model.InitialModel;
import acc.model.UserList;
import acc.view.AgentView;
import acc.view.JFrameView;

public class AgentController extends AbstractController {
	
	private String type;
	InitialModel model;
	
	public AgentController( InitialModel initModel, String option, int index, UserList list ) {
		this.type = option;
		this.model = initModel;  //save model passed to check Agent Id field and add to list later
		setModel(new AgentModel(index, list));
		setView(new AgentView((AgentModel)getModel(), this, option, index));
		((JFrameView)getView()).setVisible(true);
	}
	
	
	public void operation(String option, String id, String amount, String operations) {
		if(option.equals(AgentView.START)){   //On Start Agent button press
			boolean valid = false;
			valid = ((AgentModel)getModel()).checkStartValues( model, id, amount, operations);
			if(valid) {
				((JFrameView)getView()).setVisible(false);
				((AgentModel)getModel()).start( model, id, type, amount, operations);
				((JFrameView)getView()).setVisible(true);
			}
		} else if(option.equals(AgentView.STOP)){   //On Stop Agent button press
			((AgentModel)getModel()).stop();
		} else if(option.equals(AgentView.DISMISS)){  //On Dismiss button press
			((JFrameView)getView()).setVisible(false);
			((JFrameView)getView()).dispose();
		}else {
			//idle?
		}
	}
}