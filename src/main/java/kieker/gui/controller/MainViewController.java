/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.gui.controller;

import kieker.gui.model.DataModel;
import kieker.gui.model.MainViewModel;
import kieker.gui.model.MainViewModel.SubView;
import kieker.gui.model.PropertiesModel;
import kieker.gui.view.ISubView;
import kieker.gui.view.MainView;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public final class MainViewController implements SelectionListener {

	private final DataModel dataModel;
	private final MainViewModel mainViewModel;
	private final PropertiesModel propertiesModel;
	private final MainView view;

	public MainViewController() {
		// Create the top models
		this.dataModel = new DataModel();
		this.propertiesModel = new PropertiesModel();

		// Create the sub-controllers
		final ISubController subViewController1 = new RecordsSubViewController(this.dataModel);
		final ISubController subViewController2 = new TracesSubViewController(this.dataModel, this.propertiesModel);
		final ISubController subViewController3 = new FailedTracesSubViewController(this.dataModel, this.propertiesModel);
		final ISubController subViewController4 = new AggregatedTracesSubViewController(this.dataModel, this.propertiesModel);
		final ISubController subViewController5 = new FailureContainingTracesSubViewController(this.dataModel, this.propertiesModel);

		// Get the sub-views from the controllers
		final ISubView subView1 = subViewController1.getView();
		final ISubView subView2 = subViewController2.getView();
		final ISubView subView3 = subViewController3.getView();
		final ISubView subView4 = subViewController4.getView();
		final ISubView subView5 = subViewController5.getView();

		// Create the main model and the main view
		this.mainViewModel = new MainViewModel();
		this.view = new MainView(this.dataModel, this.mainViewModel, this, subView1, subView2, subView3, subView4, subView5);
	}

	public void showView() {
		this.view.show();
	}

	@Override
	public void widgetSelected(final SelectionEvent e) {
		this.handlePotentialTreeSelection(e);
		this.handlePotentialMenuSelection(e);
		this.handlePotentialPropertiesSelection(e);
	}

	private void handlePotentialPropertiesSelection(final SelectionEvent e) {
		if (e.widget == this.view.getMntmShortOperationNames()) {
			this.propertiesModel.setShortOperationNames(true);
		}
		if (e.widget == this.view.getMntmLongOperationNames()) {
			this.propertiesModel.setShortOperationNames(false);
		}
		if (e.widget == this.view.getMntmShortComponentNames()) {
			this.propertiesModel.setShortComponentNames(true);
		}
		if (e.widget == this.view.getMntmLongComponentNames()) {
			this.propertiesModel.setShortComponentNames(false);
		}
	}

	private void handlePotentialMenuSelection(final SelectionEvent e) {
		if (e.widget == this.view.getMntmOpenMonitoringLog()) {
			final String selectedDirectory = this.view.getDialog().open();

			if (null != selectedDirectory) {
				this.dataModel.loadMonitoringLogFromFS(selectedDirectory);
			}
		}

		if (e.widget == this.view.getMntmExit()) {
			this.view.close();
		}
	}

	private void handlePotentialTreeSelection(final SelectionEvent e) {
		if (e.item == this.view.getTrtmExplorer()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.NONE);
		}
		if (e.item == this.view.getTrtmRecords()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.RECORDS_SUB_VIEW);
		}
		if (e.item == this.view.getTrtmTraces()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.TRACES_SUB_VIEW);
		}
		if (e.item == this.view.getTrtmAggregatedTraces()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.AGGREGATED_TRACES_SUB_VIEW);
		}
		if (e.item == this.view.getTrtmJustFailedTraces()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.FAILED_TRACES_SUB_VIEW);
		}
		if (e.item == this.view.getTrtmJustTracesContaining()) {
			this.mainViewModel.setCurrentActiveSubView(SubView.FAILURE_CONTAINING_TRACES_SUB_VIEW);
		}
	}

	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {

	}

}
