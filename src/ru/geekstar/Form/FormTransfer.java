package ru.geekstar.Form;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.SavingsAccount;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class FormTransfer {
    private JPanel panelTransfer;
    private JComboBox comboBoxFrom;
    private JComboBox comboBoxTo;
    private JTextField textFieldSum;
    private JButton buttonTransfer;
    private JButton buttonCancel;

    public JPanel getPanelTransfer() {
        return panelTransfer;
    }


    public FormTransfer() {
        buttonTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // извлекаем данные, которые указал пользователь
                Object objFrom = comboBoxFrom.getSelectedItem();
                Object objTo = comboBoxTo.getSelectedItem();
                float sumTransfer = Float.valueOf(textFieldSum.getText());

                // проверяем заполнены ли все обязательные поля
                // если хоть одно поле не заполнено, то происходит return (выход из метода)
                if (!checkFillFields()) return;

                // если объектом списания является карта
                if (objFrom instanceof Card) {
                    // явно приводим Object к типу Card
                    Card cardFrom = (Card) objFrom;
                    // проверяем баланс карты
                    if (!checkBalance(cardFrom, sumTransfer)) return;

                    // если объектом зачисления является карта
                    if (objTo instanceof Card) {
                        Card cardTo = (Card) objTo;
                        // то вызываем метод пополнения transferCard2Card()
                        FormMain.physicalPerson.transferCard2Card(cardFrom, cardTo, sumTransfer);
                    }

                    // если объектом зачисления является счёт
                    if (objTo instanceof Account) {
                        Account accountTo = (Account) objTo;
                        // вызываем метод transferCard2Account()
                        FormMain.physicalPerson.transferCard2Account(cardFrom, accountTo, sumTransfer);
                    }
                }

                // если объектом списания является счёт
                if (objFrom instanceof Account) {
                    Account accountFrom = (Account) objFrom;
                    // проверяем баланс счёта
                    if (!checkBalance(accountFrom, sumTransfer)) return;

                    // если объектом зачисления является карта
                    if (objTo instanceof Card) {
                        Card cardTo = (Card) objTo;
                        // то вызываем метод transferAccount2Card()
                        FormMain.physicalPerson.transferAccount2Card(accountFrom, cardTo, sumTransfer);
                    }

                    // если объектом зачисления является счёт
                    if (objTo instanceof Account) {
                        Account accountTo = (Account) objTo;
                        // то вызываем метод пополнения transferAccount2Account()
                        FormMain.physicalPerson.transferAccount2Account(accountFrom, accountTo, sumTransfer);
                    }
                }

                // отображаем панель со статусом операции
                FormMain.formMain.displayPanelStatus("Перевод обрабатывается");
            }
        });

        // слушатель срабатывает в момент показа панели panelDepositing
        panelTransfer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                updatePanelTransfer();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMain.formMain.displayPanelMain();
            }
        });
    }

    public void updatePanelTransfer() {
        // очищаем список с картами и счетами списания
        comboBoxFrom.removeAllItems();
        // очищаем список с картами и счетами зачисления
        comboBoxTo.removeAllItems();
        // очищаем поле с суммой
        textFieldSum.setText("");

        // запрашиваем профили пользователя
        ArrayList<PhysicalPersonProfile> profiles = FormMain.physicalPerson.getPhysicalPersonProfiles();
        // перебираем профили пользователя
        for (PhysicalPersonProfile profile : profiles) {
            // запрашиваем карты профиля пользователя
            ArrayList<Card> cards = profile.getCards();
            // перебираем карты
            for (Card card : cards) {
                // добавляем в список карты для списания и зачисления
                comboBoxFrom.addItem(card);
                comboBoxTo.addItem(card);
            }

            // запрашиваем счета профиля пользователя
            ArrayList<Account> accounts = profile.getAccounts();
            // перебираем счета
            for (Account account : accounts) {
                // добавляем в список только сберегательные счета для списания и зачисления
                if (account instanceof SavingsAccount) {
                    comboBoxFrom.addItem(account);
                    comboBoxTo.addItem(account);
                }
            }
        }
    }

    public boolean checkFillFields() {
        // Регулярное выражение для проверки суммы на её корректность.
        // Целая часть может быть от 1 до 7 символов {1,7}, то есть сумма не может быть больше 9999999.
        // Дробная часть не обязательна (?), должна содержать символ точка "." и не может превышать 2 символов {2}, то есть не может быть больше 99.
        String regexSum = "[0-9]{1,7}(\\.[0-9]{2})?";
        if (!textFieldSum.getText().matches(regexSum)) {
            JOptionPane.showMessageDialog(panelTransfer, "Сумма может состоять из цифр и точки.\nСумма не может быть больше или равна 10 млн.");
            return false;
        }

        if (Float.valueOf(textFieldSum.getText()) == 0) {
            JOptionPane.showMessageDialog(panelTransfer, "Введите сумму больше 0");
            return false;
        }

        return true;
    }

    public boolean checkBalance(Card cardFrom, float sumTransfer) {
        if (cardFrom.getPayCardAccount().getBalance() < sumTransfer) {
            JOptionPane.showMessageDialog(panelTransfer, "Недостаточно средств на карте");
            return false;
        }
        return true;
    }

    public boolean checkBalance(Account accountFrom, float sumTransfer) {
        if (accountFrom.getBalance() < sumTransfer) {
            JOptionPane.showMessageDialog(panelTransfer, "Недостаточно средств на счёте");
            return false;
        }
        return true;
    }

}
