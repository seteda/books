

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * InternationalizedMortgageCalculator requires the following files:
 * 
 *   resources/Resources_ar.properties
 *   resources/Resources_fr.properties
 *   resources/Resources.properties
 *   
 * It implements a mortgage calculator that uses four JFormattedTextFields.
 */
public class InternationalizedMortgageCalculator extends JPanel implements
    PropertyChangeListener {

  private static JFrame frame;

  protected String newline = "\n";
  protected Action englishUSLocaleAction, englishUKLocaleAction,
      frenchFranceLocaleAction, frenchCanadaLocaleAction,
      arabicSaudiArabiaLocaleAction;

  private static ResourceBundle labels;

  // Values for the fields
  private double amount = 100000;
  private double rate = 7.5; // 7.5%
  private int numPeriods = 30;

  // Labels to identify the fields
  private JLabel amountLabel;
  private JLabel rateLabel;
  private JLabel numPeriodsLabel;
  private JLabel paymentLabel;

  // Fields for data entry
  private JFormattedTextField amountField;
  private JFormattedTextField rateField;
  private JFormattedTextField numPeriodsField;
  private JFormattedTextField paymentField;

  // Formats to format and parse numbers
  private static Currency currencyInstance;
  private NumberFormat amountFormat;
  private NumberFormat percentFormat;
  private NumberFormat paymentFormat;

  public InternationalizedMortgageCalculator(Locale currentLocale) {

    currencyInstance = Currency.getInstance(currentLocale);
    
    englishUSLocaleAction = new ChangeLocaleAction(
        "English, United States locale, en-US", "This is the English locale",
        new Integer(KeyEvent.VK_U), new Locale.Builder().setLanguage("en")
            .setRegion("US").build());

    englishUKLocaleAction = new ChangeLocaleAction(
        "English, United Kingdom locale, en-UK", "This is the English locale",
        new Integer(KeyEvent.VK_G), new Locale.Builder().setLanguage("en")
            .setRegion("GB").build());

    frenchFranceLocaleAction = new ChangeLocaleAction(
        "French, France locale, fr-FR", "This is the French locale",
        new Integer(KeyEvent.VK_F), new Locale.Builder().setLanguage("fr")
            .setRegion("FR").build());

    frenchCanadaLocaleAction = new ChangeLocaleAction(
        "French, Canada locale, fr-CA", "This is the French locale",
        new Integer(KeyEvent.VK_C), new Locale.Builder().setLanguage("fr")
            .setRegion("CA").build());

    arabicSaudiArabiaLocaleAction = new ChangeLocaleAction(
        "Arabic, Saudi Arabia locale, ar-SA", "This is the Arabic locale",
        new Integer(KeyEvent.VK_S), new Locale.Builder().setLanguage("ar")
            .setRegion("SA").build());

    setUpFormats(currentLocale);

    double payment = computePayment(amount, rate, numPeriods);

    // Create the labels.

    labels = ResourceBundle.getBundle("resources.Resources", currentLocale);

    amountLabel = new JLabel(MessageFormat.format(
        labels.getString("AMOUNT_STRING"),
        currencyInstance.getDisplayName(currentLocale),
        currencyInstance.getSymbol(currentLocale)));

    rateLabel = new JLabel(labels.getString("RATE_STRING"));
    numPeriodsLabel = new JLabel(labels.getString("NUM_PERIODS_STRING"));
    paymentLabel = new JLabel(labels.getString("PAYMENT_STRING"));

    // Create the text fields and set them up.

    amountField = new JFormattedTextField(amountFormat);
    amountField.setValue(new Double(amount));
    amountField.setColumns(10);
    amountField.addPropertyChangeListener("value", this);

    rateField = new JFormattedTextField(percentFormat);
    rateField.setValue(new Double(rate));
    rateField.setColumns(10);
    rateField.addPropertyChangeListener("value", this);

    numPeriodsField = new JFormattedTextField();
    numPeriodsField.setValue(new Integer(numPeriods));
    numPeriodsField.setColumns(10);
    numPeriodsField.addPropertyChangeListener("value", this);

    paymentField = new JFormattedTextField(paymentFormat);
    paymentField.setValue(new Double(payment));
    paymentField.setColumns(10);
    paymentField.setEditable(false);
    paymentField.setForeground(Color.red);

    // Tell accessibility tools about label/textfield pairs.

    amountLabel.setLabelFor(amountField);
    rateLabel.setLabelFor(rateField);
    numPeriodsLabel.setLabelFor(numPeriodsField);
    paymentLabel.setLabelFor(paymentField);

    // Layout the labels and text fieds in a GridLayout

    JPanel labelsAndFieldsPane = new JPanel(new GridLayout(4, 2));
    labelsAndFieldsPane.add(amountLabel);
    labelsAndFieldsPane.add(amountField);
    labelsAndFieldsPane.add(rateLabel);
    labelsAndFieldsPane.add(rateField);
    labelsAndFieldsPane.add(numPeriodsLabel);
    labelsAndFieldsPane.add(numPeriodsField);
    labelsAndFieldsPane.add(paymentLabel);
    labelsAndFieldsPane.add(paymentField);
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    add(labelsAndFieldsPane, BorderLayout.CENTER);
  }

  public JMenuBar createMenuBar() {
    JMenuItem menuItem = null;
    JMenuBar menuBar;

    // Create the menu bar.
    menuBar = new JMenuBar();

    // Create the first menu.
    JMenu mainMenu = new JMenu(labels.getString("LOCALE"));

    Action[] actions = { englishUSLocaleAction, englishUKLocaleAction,
        frenchFranceLocaleAction, frenchCanadaLocaleAction,
        arabicSaudiArabiaLocaleAction };

    for (int i = 0; i < actions.length; i++) {
      menuItem = new JMenuItem(actions[i]);
      menuItem.setIcon(null); // arbitrarily chose not to use icon
      mainMenu.add(menuItem);
    }

    // Set up the menu bar.
    menuBar.add(mainMenu);
    return menuBar;
  }

  public class ChangeLocaleAction extends AbstractAction {
    private String actionDesc;
    private Locale currentLocale;

    public ChangeLocaleAction(String textArg, String descArg,
        Integer mnemonicArg, Locale localeArg) {
      super(textArg);
      actionDesc = descArg;
      currentLocale = localeArg;
      putValue(SHORT_DESCRIPTION, descArg);
      putValue(MNEMONIC_KEY, mnemonicArg);
    }

    public void actionPerformed(ActionEvent e) {
      createAndShowGUI(currentLocale);
    }
  }

  /** Called when a field's "value" property changes. */
  public void propertyChange(PropertyChangeEvent e) {
    Object source = e.getSource();
    if (source == amountField) {
      amount = ((Number) amountField.getValue()).doubleValue();
    } else if (source == rateField) {
      rate = ((Number) rateField.getValue()).doubleValue();
    } else if (source == numPeriodsField) {
      numPeriods = ((Number) numPeriodsField.getValue()).intValue();
    }

    double payment = computePayment(amount, rate, numPeriods);
    paymentField.setValue(new Double(payment));
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event dispatch thread.
   */
  private static void createAndShowGUI(Locale currentLocale) {
    // Create and set up the window.

    InternationalizedMortgageCalculator demo = new InternationalizedMortgageCalculator(
        currentLocale);

    if (frame == null) {
      frame = new JFrame(labels.getString("WINDOW_TITLE"));
    } else {
      frame.getContentPane().removeAll();
      frame.setTitle(labels.getString("WINDOW_TITLE"));
    }

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add contents to the window.

    frame.add(demo);
    frame.setJMenuBar(demo.createMenuBar());
    frame.applyComponentOrientation(ComponentOrientation
        .getOrientation(currentLocale));

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    
    // Schedule a job for the event dispatch thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        // Turn off metal's use of bold fonts
        Locale currentLocale = new Locale.Builder().setLanguage("en")
            .setRegion("US").build();
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        createAndShowGUI(currentLocale);
      }
    });
  }

  // Compute the monthly payment based on the loan amount,
  // APR, and length of loan.
  double computePayment(double loanAmt, double rate, int numPeriods) {
    double I, partial1, denominator, answer;

    numPeriods *= 12; // get number of months
    if (rate > 0.01) {
      I = rate / 100.0 / 12.0; // get monthly rate from annual
      partial1 = Math.pow((1 + I), (0.0 - numPeriods));
      denominator = (1 - partial1) / I;
    } else { // rate ~= 0
      denominator = numPeriods;
    }

    answer = (-1 * loanAmt) / denominator;
    return answer;
  }

  // Create and set up number formats. These objects also
  // parse numbers input by user.
  private void setUpFormats(Locale currentLocale) {
    amountFormat = NumberFormat.getNumberInstance();

    percentFormat = NumberFormat.getNumberInstance();
    percentFormat.setMinimumFractionDigits(3);

    paymentFormat = NumberFormat.getCurrencyInstance(currentLocale);
    paymentFormat
        .setCurrency(currencyInstance);
  }
}
