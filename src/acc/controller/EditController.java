package acc.controller;

import acc.model.EditModel;
import acc.model.UserList;
import acc.view.EditView;
import acc.view.JFrameView;

public class EditController extends AbstractController {

	public EditController( String currency, int index, UserList list ) {
		setModel(new EditModel( currency, index, list ));
		setView(new EditView((EditModel)getModel(), this, currency, index));
		((JFrameView)getView()).setVisible(true);
	}
	
	public String getTitle() {
		return ( ((EditModel)getModel()).getTitle() );
	}
	
	public void operation( String option, String amount ) {
		if(option.equals(EditView.DEPOSIT)){
			((EditModel)getModel()).deposit(amount);
		} else if(option.equals(EditView.WITHDRAW)){
			((EditModel)getModel()).withdraw(amount);
		} else if(option.equals(EditView.DISMISS)){
			((JFrameView)getView()).setVisible(false);
			((JFrameView)getView()).dispose();
		}else {
			//idle?
		}
	}

	public String getAmount() {
		return ( ((EditModel)getModel()).getAmount() );
	}
}
