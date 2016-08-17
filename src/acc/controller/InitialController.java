package acc.controller;

import java.io.FileNotFoundException;
import acc.model.InitialModel;
import acc.view.InitialView;
import acc.view.JFrameView;

public class InitialController extends AbstractController {
	
	public InitialController( String file ) throws FileNotFoundException{
		setModel(new InitialModel());
		((InitialModel)getModel()).fillList(file);
		setView(new InitialView((InitialModel)getModel(), this));
		((JFrameView)getView()).setVisible(true);
	}
	
	public String[] createComboList() {
		return ( ((InitialModel)getModel()).createComboList() );
	}
	
	public void operation(String option) throws FileNotFoundException{
		if(option.equals(InitialView.USD)){
			((InitialModel)getModel()).edit( 1 );
		} else if(option.equals(InitialView.EURO)){
			((InitialModel)getModel()).edit( 2 );
		} else if(option.equals(InitialView.YUAN)){
			((InitialModel)getModel()).edit( 3 );
		} else if(option.equals(InitialView.AGENTD)){
			((InitialModel)getModel()).agent( "Deposit" );
		}else if(option.equals(InitialView.AGENTW)){
			((InitialModel)getModel()).agent( "Withdraw" );
		} else if(option.equals(InitialView.SAVE)){
			((InitialModel)getModel()).save();
		}else if(option.equals(InitialView.EXIT)){
			((InitialModel)getModel()).save();
			System.exit(0);
		}else {
			//idle?
		}
	}
	public void updateSelection( int index ) {
		((InitialModel)getModel()).updateSelection( index );
	}
}