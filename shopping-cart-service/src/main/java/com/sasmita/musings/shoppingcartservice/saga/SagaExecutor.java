package com.sasmita.musings.shoppingcartservice.saga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface SagaExecutor<C> {

	default void execute(C c) throws Throwable {
		List<SagaStage<C>> executedSagas = new ArrayList<>();
		Throwable t = null;
		Boolean isSuccess = Boolean.TRUE;
		for (SagaStage<C> stage : getSagaStages()) {
			try {
				if (!stage.isEnabled()) {
					continue;
				}
				stage.onSuccess(c);
				executedSagas.add(stage);
			} catch (Exception e) {
				t = e;
				isSuccess = Boolean.FALSE;
				break;
			}
		}
		
		if (!isSuccess) {
			Collections.reverse(executedSagas);
			for (SagaStage<C> stage : executedSagas) {
				try {
					stage.onFailure(c);
				} catch (Exception e) {
					t = e;
					isSuccess = Boolean.FALSE;
				}
			}
			throw t;
		}

	}

	List<SagaStage<C>> getSagaStages();

}
