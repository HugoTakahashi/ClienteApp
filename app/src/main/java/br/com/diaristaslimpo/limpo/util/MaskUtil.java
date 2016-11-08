package br.com.diaristaslimpo.limpo.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by jrvansuita on 17/11/15.
 * GitHub https://github.com/jrvansuita/InscricoesBR
 */

public abstract class MaskUtil {

    public enum MaskType {
        CNPJ("##.###.###/####-##"), CPF("###.###.###-##"), CEP("#####-###"), TEL("#####-####");

        String mask;

        MaskType(String s) {
            mask = s;
        }

        public String getMask() {
            return mask;
        }
    }


    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[ ]", "").replaceAll("[)]", "");
    }

    public static String mask(MaskType type, String s) {
        String result = s;

        if (!s.contains(".")) {
            String str = MaskUtil.unmask(s.toString());
            result = "";

            int i = 0;
            for (char m : type.getMask().toCharArray()) {
                if (m != '#') {
                    result += m;
                    continue;
                }
                try {
                    result += str.charAt(i);
                } catch (Exception e) {
                    break;
                }
                i++;
            }
        }

        return result;
    }


    public static TextWatcher insert(final MaskType type, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (isUpdating){
                    isUpdating = false;
                    old = s.toString();
                    return;
                }

                if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                    String str = MaskUtil.unmask(s.toString());
                    String mask = "";

                    int i = 0;
                    for (char m : type.getMask().toCharArray()) {
                        if (m != '#') {
                            mask += m;
                            continue;
                        }
                        try {
                            mask += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mask);
                    ediTxt.setSelection(mask.length());
                }else{
                    old = s.toString();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

}