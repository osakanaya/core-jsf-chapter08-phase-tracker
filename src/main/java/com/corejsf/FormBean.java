package com.corejsf;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.FactoryFinder;
import javax.faces.event.PhaseListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@RequestScoped
public class FormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private SelectItem[] phases = { new SelectItem("RESTORE_VIEW"),
			new SelectItem("APPLY_REQUEST_VALUES"),
			new SelectItem("PROCESS_VALIDATIONS"),
			new SelectItem("UPDATE_MODEL_VALUES"),
			new SelectItem("INVOKE_APPLICATION"),
			new SelectItem("RENDER_RESPONSE"), 
			new SelectItem("ANY_PHASE") 
	};
	
	public SelectItem[] getPhases() {
		return phases;
	}
	
	public void phaseChange(ValueChangeEvent event) {
		LifecycleFactory factory 
			= (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle = factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		
		PhaseListener[] listeners = lifecycle.getPhaseListeners();
		for(int i = 0; i < listeners.length; i++) {
			PhaseListener listener = listeners[i];
			if (listener instanceof com.corejsf.PhaseTracker) {
				((com.corejsf.PhaseTracker)listener).setPhase((String)event.getNewValue());
			}
		}
	}
	
	
}
