package com.douzone.mysite.web.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("write".equals(actionName)){
			action = new WriteAction();
		} else if("delete".equals(actionName)){
			action = new DeleteAction();
		} else if("search".equals(actionName)){
			action = new SearchAction();
		} else if("updateForm".equals(actionName)){
			action = new UpdateFormAction();
		} else if("update".equals(actionName)){
			action = new UpdateAction();
		} else if("keyword".equals(actionName)){
			action = new KeywordAction();
		} else {
			action = new ListAction();
		}
		return action;
	}

}
