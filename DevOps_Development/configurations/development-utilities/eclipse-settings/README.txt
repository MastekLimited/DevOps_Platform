A. Importing Preferences
	If you have eclipse 4.4 installed there is a bug in eclipse with perspectives. Please use "ALTERNATIVE STEPS FOR ECLIPSE 4.4" section.
	
	I. STEPS FOR ECLIPSE OTHER THAN 4.4
		1. Via the menu bar: File > "Import…"
		2. Select "General" / "Preferences"
		3. Click "Next >"
		4. Select the "JAVA-JS-XML-HTML-DevOpsPerspective-Other-Preferences.epf" file by clicking on browse button.
		5. Click "Finish" to perform the import.
		6. Reference for import/export preferences
			http://www.eclipseonetips.com/2010/03/22/share-eclipse-perspective-layouts-across-multiple-workspaces/
			
	II. ALTERNATIVE STEPS FOR ECLIPSE 4.4
		1. Close eclipse
		2. Replace "workbench.xmi" into {workspace_path}\.metadata\.plugins\org.eclipse.e4.workbench
		3. Start eclipse.
		4. You should have all the preferences set
		
B. Importing Working sets:
	1. Via the menu bar: File > "Import…"
	2. Select "General" / "Working Sets"
	3. Click "Next >"
	4. Select the "working-sets.wst" file by clicking on browse button
	5. Select all the working sets.
	6. Click "OK" on popup window
	7. Click "Finish" to perform the import.
	8. Reference
		i. Export working sets: http://stackoverflow.com/questions/2369829/share-export-eclipse-working-sets