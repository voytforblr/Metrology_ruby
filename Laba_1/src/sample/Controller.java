package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.Main.*;


public class Controller {


    @FXML
    private TextArea textArea;


    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label1;

    @FXML
    private Button button2;

    @FXML
    private Button button1;


    @FXML
    void initialize()  {

        LinkedList<Variables> variablesLinkedList=new LinkedList<>();


            button1.setOnAction(actionEvent -> {
            try {
                openFile();

            } catch (IOException | NullPointerException e ) {
                showErrorWindow("Произошла ошибка при открытии файла","Данные не загружены");

            }

        });
        button2.setOnAction(actionEvent -> {
         operands.clear();
         operators.clear();
         Operand.setN(0);
         Operator.setN(0);
          String str=textArea.getText();
          String str2=str;
          label3.setText(Integer.toString(find_max_nesting(str2)-1));
          counting(str);
          calculations();

        });
    }




    void openFile()throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files, (*.txt)", "*.txt"));
        BufferedReader bf = new BufferedReader(new FileReader(fileChooser.showOpenDialog(primaryStage)));
        textArea.clear();
         String str="";
        while ((str = bf.readLine()) != null) {
            textArea.appendText(str+"\n");

        }
        bf.close();
    }
    void showErrorWindow(String str1,String str2) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(str1);
        alert.setContentText(str2);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(new ButtonType("Понятно", ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.showAndWait();
    }
    void calculations(){
        int temp=0;
        temp=countCondition();
        label1.setText(Integer.toString(temp));
        label2.setText(Double.toString( (double) Math.round(((double)temp/countCondition2())*100)/100));
    }
    void counting(String str){

        str=delete_comments1(str);

        find_output(str);

        str=delete_literals1(str);
        str=delete_literals2(str);

        str=find_delete_dots(str,"\\(.+\\.\\.\\..+\\)","\\.\\.\\.");
        str=find_delete_dots(str,"\\(.+\\.\\..+\\)","\\.\\.");

        str=dot(str,"\\.");
        str=dot(str,",");

        str=find_and_delete_operators(str,"@@");
        str=find_and_delete_operators(str,"@");
        str=find_and_delete_operators(str,"\\$");

        str=puts(str);

        str=fcase(str,"case.+","case");
        str=fcase(str,"class.+","class");
        str=fcase(str,"when.*","when");

        str=mas(str);

        str=find_and_delete_operators(str,"when");
        str=find_and_delete_operators(str,"puts");
        str=find_and_delete_operators(str,"begin");
        str=find_and_delete_operators(str,"elsif");
        str=find_and_delete_operators(str,"else");
        str=find_and_delete_operators(str,"while");
        str=find_and_delete_operators(str,";");
        str=find_and_delete_operators(str,"rand");
        str=find_and_delete_operators(str,"gets");

        str=in(str);
        str=find_and_delete_operators(str,"for");
        str=eachDo(str);

        str=find_and_delete_operators(str,"redo");
        str=find_and_delete_operators(str,"next");
        str=find_and_delete_operators(str,"break\n");
        str=find_and_delete_operators(str,"end");
        str=find_and_delete_operators(str,"until");
        str=find_and_delete_operators(str,"if");


        str=find_and_delete_operators(str,"print");
        str=find_and_delete_operators(str,"printf");
        str=find_and_delete_operators(str,"gets");
        str=find_and_delete_operators(str,"each do");
        str=find_and_delete_operators(str,"do");
        str=find_and_delete_operators(str,"return");
        str=find_and_delete_operators(str,"defined\\?");
        str=find_and_delete_operators(str,"unless");
        str=find_and_delete_operators(str,"\\.eql\\?");
        str=find_and_delete_operators(str,"\\.equal\\?");
        str=find_and_delete_comparable(str,".+===.+","===");
        System.out.println("1111111111111111111111111111111111111");

        str=find_and_delete_comparable(str,".+\\*\\*=.+","\\*\\*=");
        str=find_and_delete_comparable(str,".+\\*=.*","\\*=");
        str=find_and_delete_comparable(str,".+\\+=.*","\\+=");
        str=find_and_delete_comparable(str,".+-=.*","-=");
        str=find_and_delete_comparable(str,".+/=.*","/=");
        str=find_and_delete_comparable(str,".+%=.*","%=");

        str=find_and_delete_comparable(str,".+<=>.+","<=>");
        str=find_and_delete_comparable(str,".+==.+","==");
        str=find_and_delete_comparable(str,".+>=.+",">=");
        str=find_and_delete_comparable(str,".+<=.+","<=");
        str=find_and_delete_comparable(str,".+!=.+","!=");
        str=find_and_delete_comparable(str,".+>.+",">");
        str=find_and_delete_comparable(str,".+<.+","<");
        str=find_and_delete_comparable(str,".+=.*","=");

        str=find_and_delete_operands(str,".+\\+.+");
        str=find_and_delete_operands(str,".+\\-.+");
        str=find_and_delete_operands(str,".+\\*\\*.+");
        str=find_and_delete_operands(str,".+\\*.+");
        str=find_and_delete_operands(str,".+/.+");
        str=find_and_delete_operands(str,".+%.+");


        str=find_and_delete_operands(str,".+\\&\\&.+");
        str=find_and_delete_operands(str,".+\\|\\|.+");
        str=find_and_delete_operands(str,".+or.+");
        str=find_and_delete_operands(str,".+and.+");
        str=find_and_delete_operands(str,".+!.+");
        str=find_and_delete_operands(str,".+not.+");

        str=dot(str,"def");
        str=find_and_delete_metods1(str);
        str=find_and_delete_operators(str,"def");
        int q=position_of_operand("");
        if(q!=-1){
            operands.remove(q);
        }
        System.out.println(str);

    }
    String find_and_delete_operators(String str,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){
            int start=matcher.start();
            int end=matcher.end();
            String temp=str.substring(start,end);
            if(position_of_operator(temp)!=-1){
                operators.get(position_of_operator(temp)).f1j_increment();
            }else{
                operators.add(new Operator(temp));
            }

        }
        return matcher.replaceAll("");
    }

    int position_of_operand(String operand){
        for(int i=0;i<operands.size();i++){
            if(operand.equals(operands.get(i).getOperand())){
                return i ;
            }
        }
        return -1;
    }

    int position_of_operator(String operator){
        for(int i=0;i<operators.size();i++){
            if(operator.equals(operators.get(i).getOperator())){
                return i ;
            }
        }
        return -1;
    }

    String delete_comments1(String str){
        Pattern pattern=Pattern.compile("^#[^{].+");
        Matcher matcher=pattern.matcher(str);
        return matcher.replaceAll("");
    }

    String delete_literals2(String str) {
        Pattern pattern = Pattern.compile("\".+\"");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            int start=matcher.start();
            int end=matcher.end();
            String temp=str.substring(start,end);
            if (position_of_operand(temp) != -1) {
                operands.get(position_of_operand(temp)).f2i_increment();
            } else {
                operands.add(new Operand(temp));
            }
        }
        return matcher.replaceAll("");
    }

    String delete_literals1(String str) {
        Pattern pattern = Pattern.compile("\\(\".+\"\\)");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            int start=matcher.start();
            int end=matcher.end();
            String temp=str.substring(start+1,end-1);

            System.out.println(temp);

            if (position_of_operand(temp) != -1) {
                operands.get(position_of_operand(temp)).f2i_increment();
            } else {
                operands.add(new Operand(temp));
            }
        }
        return matcher.replaceAll("");
    }

    void find_output(String str){
        Pattern pattern=Pattern.compile("#.+?}");
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()){
            if(position_of_operator("#{}")!=-1){
                operators.get(position_of_operator("#{}")).f1j_increment();
            }else{
                operators.add(new Operator("#{}"));
            }
            int start=matcher.start()+2;
            int end=matcher.end()-1;
            String temp=str.substring(start,end);
            String[] temp1=temp.split(",");
            for(int i=0;i<temp1.length;i++){
                if(position_of_operand(temp1[i])!=-1){
                    operands.get(position_of_operand(temp1[i])).f2i_increment();
                }else{
                    operands.add(new Operand(temp1[i]));
                }
            }

        }
    }


    String find_in_str(String str,String reg,int start,int end){
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher=pattern.matcher(str);
        boolean bool=matcher.find();
        return str.substring(matcher.start()+start,matcher.end()+end);
    }

    String find_and_delete_comparable(String str, String reg1,String reg2){

        Pattern pattern=Pattern.compile(reg1);
        Matcher matcher=pattern.matcher(str);


        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp1=str.substring(start,end).replaceAll("\\s","");
            String temp2=temp1.replaceAll("[()]","");

            Pattern pattern2=Pattern.compile(reg2);
            Matcher matcher2=pattern2.matcher(temp2);

            boolean bool=matcher2.find();
            String leftPart=temp2.substring(0,matcher2.start());
            String rightPart=temp2.substring(matcher2.end());
            String middlePart=temp2.substring(matcher2.start(),matcher2.end());

            if(position_of_operator(middlePart)!=-1){
                operators.get(position_of_operator(middlePart)).f1j_increment();
            }else{
                operators.add(new Operator(middlePart));
            }

            String[] masLeft=leftPart.split("(\\|\\|)|(\\&\\&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(\\-)|(\\/)|(%)");
            for (int i=0;i<masLeft.length;i++){
            if(position_of_operand(masLeft[i])!=-1){
                operands.get(position_of_operand(masLeft[i])).f2i_increment();
            }else{
                operands.add(new Operand(masLeft[i]));
            }
            }


            String[] masRight=rightPart.split("(\\|\\|)|(\\&\\&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(\\-)|(\\/)|(%)");
            for (int i=0;i<masRight.length;i++){
                if(position_of_operand(masRight[i])!=-1){
                    operands.get(position_of_operand(masRight[i])).f2i_increment();
                }else{
                    operands.add(new Operand(masRight[i]));
                }
            }

            Pattern pattern3=Pattern.compile("(\\|\\|)|(\\&\\&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(\\-)|(\\/)|(%)");
            Matcher matcher3=pattern3.matcher(rightPart);

            while(matcher3.find()){

                String temp=rightPart.substring(matcher3.start(),matcher3.end());

                if(position_of_operator(temp)!=-1){
                    operators.get(position_of_operator(temp)).f1j_increment();
                }else{
                    operators.add(new Operator(temp));
                }
            }
        }

        return matcher.replaceAll("");
    }

    String find_delete_dots(String str,String reg1, String reg2){

        Pattern pattern=Pattern.compile(reg1);
        Matcher matcher=pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp1=str.substring(start,end).replaceAll("\\s","");
            String temp2=temp1.replaceAll("[()]","");

            Pattern pattern2=Pattern.compile(reg2);
            Matcher matcher2=pattern2.matcher(temp2);

            boolean bool=matcher2.find();
            String leftPart=temp2.substring(0,matcher2.start());
            String rightPart=temp2.substring(matcher2.end());
            String middlePart=temp2.substring(matcher2.start(),matcher2.end());

            if(position_of_operator(middlePart)!=-1){
                operators.get(position_of_operator(middlePart)).f1j_increment();
            }else{
                operators.add(new Operator(middlePart));
            }

            if(position_of_operand(leftPart)!=-1){
                operands.get(position_of_operand(leftPart)).f2i_increment();
            }else{
                operands.add(new Operand(leftPart));
            }

            if(position_of_operand(rightPart)!=-1){
                operands.get(position_of_operand(rightPart)).f2i_increment();
            }else{
                operands.add(new Operand(rightPart));
            }

        }
        return matcher.replaceAll("");
    }

    String puts(String str){

        Pattern pattern = Pattern.compile("puts.+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);


            if (!(temp.length()>5)){
                continue;
            }


            String leftPart="puts";
            String rightPart=temp.substring(5);


            if(position_of_operator(leftPart)!=-1){
                operators.get(position_of_operator(leftPart)).f1j_increment();
            }else{
                operators.add(new Operator(leftPart));
            }

            if(position_of_operand(rightPart)!=-1){
                operands.get(position_of_operand(rightPart)).f2i_increment();
            }else{
                operands.add(new Operand(rightPart));
            }

        }
        return matcher.replaceAll("");
    }

    String in(String str){
        Pattern pattern = Pattern.compile("for.+in");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start+4,end-3);


            if(position_of_operand(temp)!=-1){
                operands.get(position_of_operand(temp)).f2i_increment();
            }else{
                operands.add(new Operand(temp));
            }

            if(position_of_operator("for")!=-1){
                operators.get(position_of_operator("for")).f1j_increment();
            }else{
                operators.add(new Operator("for"));
            }

        }
        return matcher.replaceAll("");
    }

    String eachDo(String str){

        Pattern pattern = Pattern.compile("\\.each do.+");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start+10,end-1);

            if(position_of_operator("each do")!=-1){
                operators.get(position_of_operator("each do")).f1j_increment();
            }else{
                operators.add(new Operator("each do"));
            }

            if(position_of_operand(temp)!=-1){
                operands.get(position_of_operand(temp)).f2i_increment();
            }else{
                operands.add(new Operand(temp));
            }

        }
        return matcher.replaceAll("");
    }

    String find_and_delete_metods1(String str) {
        ArrayList<String> names1=new ArrayList<>();
        ArrayList<String> names2=new ArrayList<>();
        Pattern pattern = Pattern.compile("def.+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            int start=matcher.start();
            int end=matcher.end();
            String name_to_end=str.substring(start+4,end).trim();
            if(name_to_end.charAt(name_to_end.length()-1)!=')'){
                String name=name_to_end;
                names2.add(name);
                if (position_of_operator(name) != -1) {
                    operators.get(position_of_operator(name)).f1j_increment();
                } else {
                    operators.add(new Operator(name));
                }


            }else{
                String name=find_in_str(name_to_end,".+\\(",0,-1);//до скобки
                names1.add(name);
                try {
                    String[] parametrs=find_in_str(name_to_end,"\\(.*\\)",1,-1).split(",");//между скобок
                    for (int i = 0; i < parametrs.length; i++) {
                        if (position_of_operand(parametrs[i]) != -1) {
                            operands.get(position_of_operand(parametrs[i])).f2i_increment();
                        } else {
                            operands.add(new Operand(parametrs[i]));
                        }
                    }
                }catch (IllegalStateException e){
                }

                if (position_of_operator(name+"()") != -1) {
                    operators.get(position_of_operator(name+"()")).f1j_increment();
                } else {
                    operators.add(new Operator(name+"()"));
                }
            }


        }
        str=matcher.replaceAll("");

        for (int i=0;i<names1.size();i++){
            Pattern pattern1 = Pattern.compile(names1.get(i)+".*");//до конца
            Matcher matcher1 = pattern1.matcher(str);
            while (matcher1.find()) {
                int start=matcher1.start();
                int end=matcher1.end();
                String name_to_end=str.substring(start,end);
                try {
                    String[] parametrs=find_in_str(name_to_end,"\\(.+\\)",1,-1).split(",");//между скобок
                    for (int j = 0; j < parametrs.length; j++) {
                        if (position_of_operand(parametrs[j]) != -1) {
                            operands.get(position_of_operand(parametrs[j])).f2i_increment();
                        } else {
                            operands.add(new Operand(parametrs[j]));
                        }
                    }
                }catch (IllegalStateException e){
                }

                if (position_of_operator(names1.get(i)+"()") != -1) {
                    operators.get(position_of_operator(names1.get(i)+"()")).f1j_increment();
                } else {
                    operators.add(new Operator(names1.get(i)+"()"));
                }
            }
            str=matcher1.replaceAll("");
        }


        for (int i=0;i<names2.size();i++){
             Pattern pattern2 = Pattern.compile(names2.get(i)+".*");//до конца
             Matcher matcher2 = pattern2.matcher(str);
            while (matcher2.find()){
                if (position_of_operator(names2.get(i)) != -1) {
                    operators.get(position_of_operator(names2.get(i))).f1j_increment();
                } else {
                    operators.add(new Operator(names2.get(i)));
                }
            }
            str=matcher2.replaceAll("");
        }



        return str;
    }

    String find_and_delete_operands(String str,String reg){

        Pattern pattern=Pattern.compile(reg);
        Matcher matcher=pattern.matcher(str);


        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp1=str.substring(start,end).replaceAll("\\s","");
            String temp2=temp1.replaceAll("[()]","");

            String[] masRight=temp2.split("(\\|\\|)|(\\&\\&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(\\-)|(\\/)|(%)");
            for (int i=0;i<masRight.length;i++){
                if(position_of_operand(masRight[i])!=-1){
                    operands.get(position_of_operand(masRight[i])).f2i_increment();
                }else{
                    operands.add(new Operand(masRight[i]));
                }
            }

            Pattern pattern3=Pattern.compile("(\\|\\|)|(\\&\\&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(\\-)|(\\/)|(%)");
            Matcher matcher3=pattern3.matcher(temp2);

            while(matcher3.find()){

                String temp=temp2.substring(matcher3.start(),matcher3.end());

                if(position_of_operator(temp)!=-1){
                    operators.get(position_of_operator(temp)).f1j_increment();
                }else{
                    operators.add(new Operator(temp));
                }
            }
            str=matcher.replaceFirst("");
        }

        return str;
    }

    String dot(String str,String reg){
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){
            int start=matcher.start();
            int end=matcher.end();
            String temp=str.substring(start,end);
            if(position_of_operator(temp)!=-1){
                operators.get(position_of_operator(temp)).f1j_increment();
            }else{
                operators.add(new Operator(temp));
            }

        }
        return str;
    }

    String fcase(String str,String reg1, String reg2){
        Pattern pattern = Pattern.compile(reg1);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);

            Pattern pattern2=Pattern.compile(reg2);
            Matcher matcher2=pattern2.matcher(temp);

            if(position_of_operator(reg2)!=-1){
                operators.get(position_of_operator(reg2)).f1j_increment();
            }else{
                operators.add(new Operator(reg2));
            }

            matcher2.find();

            if(position_of_operand(temp.substring(matcher2.end()+1))!=-1){
                operands.get(position_of_operand(temp.substring(matcher2.end()+1))).f2i_increment();
            }else{
                operands.add(new Operand(temp.substring(matcher2.end()+1)));
            }
        }

        return matcher.replaceAll("");
    }

    String mas(String str){
        Pattern pattern = Pattern.compile("\\[.*\\]");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start+1,end-1);


            if(position_of_operator("[]")!=-1){
                operators.get(position_of_operator("[]")).f1j_increment();
            }else{
                operators.add(new Operator("[]"));
            }
            String[] mas=temp.split(",");
            for (int i=0;i<mas.length;i++){
            if(position_of_operand(mas[i])!=-1){
                operands.get(position_of_operand(mas[i])).f2i_increment();
            }else{
                operands.add(new Operand(mas[i]));
            }}
        }

        return matcher.replaceAll("");
    }

    int find_max_nesting(String str)
    {
        int max_level=0;

        Pattern pattern = Pattern.compile("(def)|(if)|(end)|(when)|(for)|(while)|(unless)|(until)|(elsif)|(case)|(begin)");
        Matcher matcher = pattern.matcher(str);

        Stack stack=new Stack();
        int i=0;
        int max=0;
        int ecount=0;
        boolean flagcase=true;

        boolean whenflag=true;

        while (matcher.find())
        {
            int start=matcher.start();
            int end=matcher.end();
            String temp=str.substring(start,end);

            if (temp.equals("end"))
            {
                stack.push(')');
                i--;
                System.out.println(stack.peek()+" "+max+" "+i);

                if (i==0 && stack.search('d')==-1 && stack.search('c')==-1){
                    stack.clear();
                    if (!flagcase)
                    {
                        max_level=Math.max(max,max_level);
                        flagcase=true;
                    }else
                    {
                        max_level=Math.max(max,max_level);
                    }

                    max=0;
                    ecount=0;
                    continue;
                }


                if (i==0 && stack.search('d')!=-1)
                {
                    Stack stack1=new Stack();
                    stack.pop();
                    max=0;
                    ecount=0;

                    while(!stack.empty())
                    {
                        stack1.push(stack.pop());
                    }

                    stack1.pop();

                    Stack stack2=new Stack();

                    while(!stack1.empty())
                    {
                        stack2.push(stack1.pop());
                    }

                    while (!stack2.empty())
                    {

                        if ((char) stack2.peek()==')')
                        {
                            i++;
                            max=Math.max(i,max);
                        }

                        if ((char) stack2.peek()=='e')
                        {

                            max++;
                        }

                        if ((char) stack2.peek()=='(')
                        {
                            i--;
                        }

                        if (i==0)
                        {
                            max_level=Math.max(max,max_level);
                            max=0;
                        }

                        stack2.pop();

                    }

                    max_level=Math.max(max,max_level);
                }

            }

            if (temp.equals("def"))
            {
                stack.push('d');
                i++;
            }

            if (temp.equals("case"))
            {
                stack.push('(');
                i++;
                max--;
                flagcase=false;
                whenflag=true;
                System.out.println(stack.peek()+" "+max+" "+i);
            }

            if (temp.equals("elsif") || temp.equals("when") )
            {
                if (temp.equals("when") && whenflag){
                    whenflag=false;
                } else {
                    stack.push('e');
                    max++;
                    ecount++;
                    System.out.println(stack.peek() + " " + max + " " + i);
                }}

            if (temp.equals("if") /*&& str.charAt(start-1)!='s'*/)
            {
                stack.push('(');
                i++;
                max=Math.max(i+ecount,max);
                System.out.println(stack.peek()+" "+max+" "+i);            }

            if ( temp.equals("for") || temp.equals("while") || temp.equals("begin") ||temp.equals("until") )
            {
                stack.push('(');
                i++;
                max=Math.max(i+ecount,max);
                System.out.println(stack.peek()+" "+max+" "+i);
            }

        }

        return max_level;
    }

    int countCondition(){
        int k=0;
        if (position_of_operator("if")!=-1){
            k+=operators.get(position_of_operator("if")).getF1j();
        }

        if (position_of_operator("while")!=-1){
            k+=operators.get(position_of_operator("while")).getF1j();
        }

        if (position_of_operator("for")!=-1){
            k+=operators.get(position_of_operator("for")).getF1j();
        }

        if (position_of_operator("elsif")!=-1){
            k+=operators.get(position_of_operator("elsif")).getF1j();
        }

        if (position_of_operator("unless")!=-1){
            k+=operators.get(position_of_operator("unless")).getF1j();
        }

        if (position_of_operator("when")!=-1){
            k+=operators.get(position_of_operator("when")).getF1j();
            System.out.println(operators.get(position_of_operator("when")).getF1j());
        }

        if (position_of_operator("eachDo")!=-1){
            k+=operators.get(position_of_operator("eachDo")).getF1j();
        }

        return k;
    }
    int countCondition2(){
        int k=0;
        for(int i=0;i<operators.size();i++){
            k+=operators.get(i).getF1j();
        }
        return k;
    }

    void add_to_var() {
        for (int i = 0; i < operands.size(); i++) {//check for methods
            if (operands.get(i).getOperand().charAt(0)!='"' && !(operands.get(i).getOperand().charAt(0)>='0' && operands.get(i).getOperand().charAt(0)<='9')){
                variables.add(new Variables(operands.get(i).getOperand(),null,null,operands.get(i).getF2i()));
            }
        }
    }

    void for_check(String str){
        Pattern pattern=Pattern.compile("for.+");
        Matcher matcher=pattern.matcher(str);


        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);
            String rep_str=temp.replaceAll("for","");
            rep_str=rep_str.replaceAll("\\.\\.",".");
            rep_str=rep_str.trim();
            rep_str=rep_str.replaceAll(" {2}"," ");

            String[] temp1=rep_str.split("(\\s)|(\\.)|(\\|\\|)|(&&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(-)|(/)|(%)|(=)");

            for(int i=0;i<temp1.length;i++){
                for (int j=0;j<variables.size();j++){
                    if (temp1[i].equals(variables.get(j).getVar())){

                        variables.get(j).setType_full("C");
                        if(variables.get(j).getType_io() != null) {
                            variables.get(j).setType_io("C");
                        }

                    }
                }
            }

        }
    }

    void puts_check(String str){
        Pattern pattern=Pattern.compile("puts.+");
        Matcher matcher=pattern.matcher(str);

        loop:while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);
            for (int i=0;i<temp.length();i++){
                if (temp.charAt(i)=='"'){
                    continue loop;
                }
            }

            String rep_str=temp.replaceAll("puts","");
            rep_str=rep_str.replaceAll(" ","");

            for (int j=0;j<variables.size();j++){
                if (rep_str.equals(variables.get(j).getVar())){

                    variables.get(j).setType_full("M");
                    variables.get(j).setType_io("M");

                }
            }
        }
    }

    void print_check(String str){
        Pattern pattern=Pattern.compile("#.+?}");
        Matcher matcher=pattern.matcher(str);
        while (matcher.find()){

            int start=matcher.start()+2;
            int end=matcher.end()-1;

            String temp=str.substring(start,end);
            String[] temp1=temp.split(",");

            for(int i=0;i<temp1.length;i++){
                for (int j=0;j<variables.size();j++){
                    if (temp1[i].equals(variables.get(j).getVar())){

                        variables.get(j).setType_full("M");
                        variables.get(j).setType_io("M");

                    }
                }
            }

        }
    }

    void check(String str, String reg, String delete, String type_full, String type_io){

        Pattern pattern=Pattern.compile(reg);
        Matcher matcher=pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);
            String rep_str=temp.replaceAll("===","=");
            rep_str=rep_str.replaceAll("==","=");
            rep_str=rep_str.replaceAll("\\+=","=");
            rep_str=rep_str.replaceAll("-=","=");
            rep_str=rep_str.replaceAll("\\*=","=");
            rep_str=rep_str.replaceAll("/=","=");
            rep_str=rep_str.replaceAll(">=","=");
            rep_str=rep_str.replaceAll("<=","=");
            rep_str=rep_str.replaceAll("!=","=");
            rep_str=rep_str.replaceAll("\\*\\*\\**","=");
            rep_str=rep_str.replaceAll("\\*\\*","=");

            rep_str=rep_str.replaceAll(delete,"");
            rep_str=rep_str.replaceAll(" ","");
            rep_str=rep_str.replaceAll("\\(","");
            rep_str=rep_str.replaceAll("\\)","");

            String[] temp1=rep_str.split("(\\|\\|)|(&&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(-)|(/)|(%)|(=)");

            for(int i=0;i<temp1.length;i++){
                for (int j=0;j<variables.size();j++){
                    if (temp1[i].equals(variables.get(j).getVar())){

                        variables.get(j).setType_full("C");
                        if(variables.get(j).getType_io() != null) {
                            variables.get(j).setType_io("C");
                        }

                    }
                }
            }

        }
    }

    void each_do_check(String str){
        Pattern pattern=Pattern.compile(".+each do.+");
        Matcher matcher=pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);
            String rep_str=temp.replaceAll("each","");
            rep_str=rep_str.replaceAll("do","");
            rep_str=rep_str.replaceAll("\\.\\.","\\.");
            rep_str=rep_str.replaceAll("\\|","");
            rep_str=rep_str.replaceAll(" ","");
            rep_str=rep_str.replaceAll("\\(","");
            rep_str=rep_str.replaceAll("\\)","");

            String[] temp1=rep_str.split("(\\s)|(\\.)|(\\|\\|)|(&&)|(and)|(or)|(!)|(not)|(\\*\\*)|(\\*)|(\\+)|(-)|(/)|(%)|(=)");

            for(int i=0;i<temp1.length;i++){
                for (int j=0;j<variables.size();j++){
                    if (temp1[i].equals(variables.get(j).getVar())){

                        variables.get(j).setType_full("C");
                        if(variables.get(j).getType_io() != null) {
                            variables.get(j).setType_io("C");
                        }

                    }
                }
            }
        }
    }

    void gets_check(String str){
        Pattern pattern=Pattern.compile(".+gets");
        Matcher matcher=pattern.matcher(str);

        while (matcher.find()){

            int start=matcher.start();
            int end=matcher.end();

            String temp=str.substring(start,end);
            String rep_str=temp.replaceAll("gets","");
            rep_str=rep_str.replaceAll("=","");
            rep_str=rep_str.replaceAll("","=");

            for (int j=0;j<variables.size();j++){
                if (rep_str.equals(variables.get(j).getVar())){

                    variables.get(j).setType_full("P");
                    variables.get(j).setType_io("P");

                }
            }
        }
    }

    void p_check(String str){
        for (int i=0;i<variables.size();i++){
            if (variables.get(i).getType_full().equals("P")) {

                String reg=variables.get(i).getVar()+"(( )|(\\+)|(\\*)|(-)|(/))?=.+";

                Pattern pattern=Pattern.compile(reg);
                Matcher matcher=pattern.matcher(str);

                while (matcher.find()) {

                    int start = matcher.start();
                    int end = matcher.end();

                    String temp = str.substring(start, end);
                    String rep_str = temp.replaceAll("===", "+");
                    rep_str = rep_str.replaceAll("gets", "=");
                    rep_str = rep_str.replaceAll(" ", "");
                    rep_str = rep_str.replaceAll("==", "+");
                    rep_str = rep_str.replaceAll("\\+=", "=");
                    rep_str = rep_str.replaceAll("\\*=", "=");
                    rep_str = rep_str.replaceAll("/=", "=");
                    rep_str = rep_str.replaceAll("-=", "=");
                    rep_str = rep_str.replaceAll(" ", "");

                    if (rep_str.charAt(variables.get(i).getVar().length())=='='){
                        variables.get(i).setType_full("M");
                        if(variables.get(i).getType_io() != null) {
                            variables.get(i).setType_io("M");
                        }
                    }

                }

                ///////////////////////////

                String reg2="(\\+\\+)|(\\*\\*)|(--)|(\\/\\/)"+variables.get(i).getVar()+".*";

                while (matcher.find()){

                    int start = matcher.start();
                    int end = matcher.end();

                    String temp = str.substring(start, end);
                    if (variables.get(i).getVar().length()+2==temp.length()){
                        variables.get(i).setType_full("M");
                        if(variables.get(i).getType_io() != null) {
                            variables.get(i).setType_io("M");
                        }
                    }

                }
            }
        }
    }
}
