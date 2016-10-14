/**
 * 
 */
package de.urszeidler.eclipse.solidity.laucher.ui;

import static de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants.GENERATE_ABI;
import static de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants.GENERATE_JS_TEST_TARGET;
import static de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants.GENERATE_MARKDOWN;
import static de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants.*;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import de.urszeidler.eclipse.solidity.ui.preferences.PreferenceConstants;

/**
 * @author urs
 *
 */
public class GenerateOthersConfigurationTab extends AbstractUml2SolidityLaunchConfigurationTab {

	private Button btnGenerateMarkdownReport;
	private Text docDirectoryText;
	private Button btnGfenerateSingleAbi;
	private Text abiDirectoryText;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.
	 * swt.widgets.Composite)
	 */
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void createControl(Composite parent) {
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, true));
		setControl(mainComposite);
		Group grpDocumentation = new Group(mainComposite, SWT.NONE);
		grpDocumentation.setLayout(new GridLayout(3, false));
		grpDocumentation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpDocumentation.setText("documentation");

		btnGenerateMarkdownReport = new Button(grpDocumentation, SWT.CHECK);
		btnGenerateMarkdownReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		btnGenerateMarkdownReport.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnGenerateMarkdownReport.setText("generate markdown report");
		new Label(grpDocumentation, SWT.NONE);

		Label lblNewLabel_1 = new Label(grpDocumentation, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("generate doc directory");

		docDirectoryText = new Text(grpDocumentation, SWT.BORDER);
		docDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnNewButton_1 = new Button(grpDocumentation, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContainer initialRoot = ResourcesPlugin.getWorkspace().getRoot();
				ContainerSelectionDialog containerSelectionDialog = new ContainerSelectionDialog(getShell(),
						initialRoot, false, "select doc folder");
				containerSelectionDialog.open();
				Object[] result = containerSelectionDialog.getResult();
				if (result != null && result.length == 1) {
					IPath container = (IPath) result[0];
					docDirectoryText.setText(container.toString());
					setDirty(true);
					updateLaunchConfigurationDialog();
				}

			}
		});
		btnNewButton_1.setText("select");

		Group grpAbi = new Group(mainComposite, SWT.NONE);
		grpAbi.setLayout(new GridLayout(3, false));
		grpAbi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpAbi.setText("abi");

		btnGfenerateSingleAbi = new Button(grpAbi, SWT.CHECK);
		btnGfenerateSingleAbi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
		btnGfenerateSingleAbi.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
		btnGfenerateSingleAbi.setText("generate single abi file");

		Label lblDirectory = new Label(grpAbi, SWT.NONE);
		lblDirectory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDirectory.setText("directory");

		abiDirectoryText = new Text(grpAbi, SWT.BORDER);
		abiDirectoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnSelect_1 = new Button(grpAbi, SWT.NONE);
		btnSelect_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContainer initialRoot = ResourcesPlugin.getWorkspace().getRoot();
				ContainerSelectionDialog containerSelectionDialog = new ContainerSelectionDialog(getShell(),
						initialRoot, false, "select abi folder");
				containerSelectionDialog.open();
				Object[] result = containerSelectionDialog.getResult();
				if (result != null && result.length == 1) {
					IPath container = (IPath) result[0];
					abiDirectoryText.setText(container.toString());
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
			}
		});
		btnSelect_1.setText("select");

		inialize();
	}

	private void inialize() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore(null);

		configuration.setAttribute(GENERATE_ABI, store.getBoolean(GENERATE_ABI));
		configuration.setAttribute(GENERATE_MARKDOWN, store.getBoolean(GENERATE_MARKDOWN));
		configuration.setAttribute(GENERATION_TARGET_DOC, store.getString(GENERATION_TARGET_DOC));
		configuration.setAttribute(GENERATE_ABI_TARGET, store.getString(GENERATE_ABI_TARGET));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.
	 * debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		IPreferenceStore store = PreferenceConstants.getPreferenceStore(null);
		try {
			btnGenerateMarkdownReport.setSelection(configuration.getAttribute(GENERATE_MARKDOWN, store.getBoolean(GENERATE_MARKDOWN)));
			btnGfenerateSingleAbi.setSelection(configuration.getAttribute(GENERATE_ABI, store.getBoolean(GENERATE_ABI)));
			docDirectoryText.setText(configuration.getAttribute(GENERATION_TARGET_DOC, store.getString(GENERATION_TARGET_DOC)));
			abiDirectoryText.setText(configuration.getAttribute(GENERATE_ABI_TARGET, store.getString(GENERATE_ABI_TARGET)));
			
			IResource member = findResource(configuration, docDirectoryText.getText());
			if (member != null)
				docDirectoryText.setText(member.getFullPath().toString());
			member = findResource(configuration, abiDirectoryText.getText());
			if (member != null)
				abiDirectoryText.setText(member.getFullPath().toString());

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(GENERATE_ABI, btnGfenerateSingleAbi.getSelection());
		configuration.setAttribute(GENERATE_MARKDOWN, btnGenerateMarkdownReport.getSelection());
		configuration.setAttribute(GENERATION_TARGET_DOC, docDirectoryText.getText());
		configuration.setAttribute(GENERATE_ABI_TARGET, abiDirectoryText.getText());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "generate doc";
	}

}