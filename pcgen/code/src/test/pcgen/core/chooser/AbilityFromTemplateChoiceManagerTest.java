/**
 * 
 */
package pcgen.core.chooser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import pcgen.AbstractCharacterTestCase;
import pcgen.core.Ability;
import pcgen.core.AbilityInfo;
import pcgen.core.Globals;
import pcgen.core.PCTemplate;
import pcgen.core.PObject;
import pcgen.core.PlayerCharacter;
import pcgen.util.TestHelper;


/**
 * @author andrew
 *
 */
public class AbilityFromTemplateChoiceManagerTest extends
		AbstractCharacterTestCase {

	/*
	 * Test method for 'pcgen.core.chooser.AbilityFromTemplateChoiceManager.addToMaps(Categorisable)'
	 */
	public void testAddToMaps() {
		PCTemplate tem = new PCTemplate();
		tem.setName("Test Template 1");
		
		AbilityFromTemplateChoiceManager choiceManager = new AbilityFromTemplateChoiceManager(tem, getCharacter());

		try
		{
			Class cMClass = choiceManager.getClass();

			Field aField  = (Field) TestHelper.findField(cMClass, "nameMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(0), "Name map is empty");
			
			aField  = (Field) TestHelper.findField(cMClass, "catMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(0), "Category map is empty");

			aField  = (Field) TestHelper.findField(cMClass, "useNameMap");
			is (aField.get(choiceManager), eq(true), "using name map");

		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}

		AbilityInfo abInfo = new AbilityInfo("foo", "bar");

		choiceManager.addToMaps(abInfo);
		
		try
		{
			Class cMClass = choiceManager.getClass();

			Field aField  = (Field) TestHelper.findField(cMClass, "nameMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(0), "Name map is still empty");
			
			aField  = (Field) TestHelper.findField(cMClass, "catMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(0), "Category map is still empty");

			aField  = (Field) TestHelper.findField(cMClass, "useNameMap");
			is (aField.get(choiceManager), eq(true), "using name map (2)");
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}
		
		Ability ab = new Ability();
		ab.setName("bar");
		ab.setCategory("foo");

		is (new Boolean(Globals.addAbility(ab)), eq(true), "First ability added correctly");
		
		choiceManager.addToMaps(abInfo);
		
		try
		{
			Class cMClass = choiceManager.getClass();

			Field aField  = (Field) TestHelper.findField(cMClass, "nameMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(1), "Name map is not empty");
			
			aField  = (Field) TestHelper.findField(cMClass, "catMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(1), "Category map is not empty");

			aField  = (Field) TestHelper.findField(cMClass, "useNameMap");
			is (aField.get(choiceManager), eq(true), "using name map (3)");
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}
		
		abInfo = new AbilityInfo("baz", "bar");

		ab = new Ability();
		ab.setName("bar");
		ab.setCategory("baz");

		is (new Boolean(Globals.addAbility(ab)), eq(true), "Second ability added correctly");
		
		choiceManager.addToMaps(abInfo);

		try
		{
			Class cMClass = choiceManager.getClass();

			Field aField  = (Field) TestHelper.findField(cMClass, "nameMap");
			HashMap name  = (HashMap) aField.get(choiceManager);
			Set sName     = name.keySet();

			Object st[]   = sName.toArray();

			is (st[0], strEq("bar"), "One");
			
			/* these next two only have one entry because the first entry is discarded
			 * when the the second is added (which is why we also have cat maps!) */
			
			is (new Integer(sName.size()), eq(1), "Name key set has only one entry");
			is (new Integer(name.size()), eq(1), "Name map has only one entry");
			
			aField  = (Field) TestHelper.findField(cMClass, "catMap");
			is (new Integer(((HashMap) aField.get(choiceManager)).size()), eq(2), "Category map has two entries");

			aField  = (Field) TestHelper.findField(cMClass, "useNameMap");
			is (aField.get(choiceManager), eq(false), "using name map (4)");
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}

	}

	/*
	 * Test method for 'pcgen.core.chooser.AbstractCategorisableChoiceManager.initialise(int, int, int)'
	 */
	public void testInitialise() {
		PCTemplate tem = new PCTemplate();
		tem.setName("Test Template 2");
		
		ChoiceManagerCategorisable choiceManager = new AbilityFromTemplateChoiceManager(tem, getCharacter());

		choiceManager.initialise(1,2,3);
		
		try
		{
			Class cMClass = choiceManager.getClass();
			
			Field aField  = (Field) TestHelper.findField(cMClass, "numberOfChoices");
			is (aField.get(choiceManager), eq(1), "Number of choices is set correctly");
			
			aField  = (Field) TestHelper.findField(cMClass, "requestedSelections");
			is (aField.get(choiceManager), eq(2), "Requested selections is set correctly");
			
			aField  = (Field) TestHelper.findField(cMClass, "maxNewSelections");
			is (aField.get(choiceManager), eq(3), "Max new Selections is set correctly");
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}

		choiceManager.initialise(23,17,7);
		
		try
		{
			Class cMClass = choiceManager.getClass();
			
			Field aField  = (Field) TestHelper.findField(cMClass, "numberOfChoices");
			is (aField.get(choiceManager), eq(23), "Number of choices is set correctly");
			
			aField  = (Field) TestHelper.findField(cMClass, "requestedSelections");
			is (aField.get(choiceManager), eq(17), "Requested selections is set correctly");
			
			aField  = (Field) TestHelper.findField(cMClass, "maxNewSelections");
			is (aField.get(choiceManager), eq(7), "Max new Selections is set correctly");
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}
	}

	/*
	 * Test method for 'pcgen.core.chooser.AbilityFromTemplateChoiceManager.AbilityFromTemplateChoiceManager(PObject, PlayerCharacter)'
	 */
	public void testAbilityFromTemplateChoiceManager() {
		PCTemplate tem = new PCTemplate();
		tem.setName("Test Template 3");
		
		ChoiceManagerCategorisable choiceManager = new AbilityFromTemplateChoiceManager(tem, getCharacter());

		try
		{
			Class cMClass = choiceManager.getClass();
			
			Field aField  = (Field) TestHelper.findField(cMClass, "pobject");
			PObject pobject = (PObject) aField.get(choiceManager);
			is(pobject.getName(), strEq("Test Template 3"));

			aField  = (Field) TestHelper.findField(cMClass, "pc");
			PlayerCharacter aPc = (PlayerCharacter) aField.get(choiceManager);
			is(aPc.getName(), strEq(getCharacter().getName()));
			
		}
		catch (IllegalAccessException e) {
			System.out.println(e);
		}

	}

	/*
	 * Test method for 'pcgen.core.chooser.AbstractCategorisableChoiceManager.doChooser(List, List, List, PlayerCharacter)'
	 */
	public void testDoChooserListListListPlayerCharacter() {

	}

	/*
	 * Test method for 'pcgen.core.chooser.AbstractCategorisableChoiceManager.doChooser(CategorisableStore, List)'
	 */
	public void testDoChooserCategorisableStoreList() {

	}

}