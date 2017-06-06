package yomii.autogendp;

import org.xml.sax.helpers.AttributesImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * dp适配文件生成工具类
 * <p>
 * Author: duke
 * <p>
 * amend by Yomii on 2017-06-05
 * <p>
 * 增加对带参数的values文件夹的适配，比如values-v19
 */
public class MainDimenSax {


    /**
     * 入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        //数组第一项为基准宽度dp，后面为需要生成的对应宽度dp
        String[] widthDps;
        //values文件夹的列表，每个参数都需要适配一次屏幕尺寸
        String[] valuesArgs = {""};

        if (args == null || args.length < 3) {
            widthDps = new String[]{"360", "384", "400", "411", "533", "640", "720", "768", "820"};
            StringBuilder outputString = new StringBuilder("Used default DPs : ");
            for (int i = 0, j = widthDps.length; i < j; i++) {
                outputString.append(widthDps[i]);
                if (i != j - 1) {
                    outputString.append(",");
                }
            }
            System.out.println(outputString.toString());
        } else {
            widthDps = args;
            for (int i = 0, j = args.length; i < j; i++) {
                if ("-a".equals(args[i]) && i != j - 1) {
                    widthDps = Arrays.copyOfRange(args, 0, i);
                    valuesArgs = new String[j - i];
                    valuesArgs[0] = "";
                    System.arraycopy(args, i + 1, valuesArgs, 1, j - i - 1);
                    break;
                }
            }
        }


        //当前根目录：Project/app/src/main/
        String baseDirPath = new File("./res/").getAbsolutePath();
        //基准dp，比喻：360dp
        float baseDP = Float.parseFloat(widthDps[0]);

        if (readAndGenerateFiles(widthDps, baseDirPath, baseDP, valuesArgs))
            System.out.println("OK ALL OVER，全部生成完毕！");
    }

    private static boolean readAndGenerateFiles(String[] args, String baseDirPath, float baseDP,
                                                String[] valuesArgs) {
        for (String valuesArg : valuesArgs) {
            //基准dimens.xml文件路径，比如：D:/android/res/values/dimens.xml
            String baseDimenFilePath = baseDirPath + "/values" + valuesArg + "/dimens.xml";
            File testBaseDimenFile = new File(baseDimenFilePath);
            //判断基准文件是否存在
            if (!testBaseDimenFile.exists()) {
                System.out.println("DK WARNING:  \"./res/values" + valuesArg + "/dimens.xml\" 路径下的文件找不到!");
                return false;
            }
            //源dimens文件的dimen集合
            ArrayList<DimenBean> list = readBaseDimenFile(baseDimenFilePath);
            if (list == null || list.size() <= 0) {
                System.out.println("DK WARNING:  \"../res/values" + valuesArg + "/dimens.xml\" 文件无数据!");
                return false;
            } else {
                System.out.println("OK \"../res/values" + valuesArg + "/dimens.xml\" 基准dimens文件解析成功!");
            }
            //循环指定的dp参数，生成对应的dimens-swXXXdp.xml文件
            for (int i = 1; i < args.length; i++) {
                //获取当前dp除以baseDP后的倍数
                float multiple = Float.parseFloat(args[i]) / baseDP;
                //创建当前dp对应的dimens文件目录
                String outPutDir = baseDirPath + "/values" + "-w" + args[i] + "dp" + valuesArg + "/";
                new File(outPutDir).mkdirs();
                //待生成的dimens文件里路径
                String outPutFile = outPutDir + "dimens.xml";
                //生成目标文件dimens.xml输出目录
                createDestinationDimens(list, multiple, outPutFile);
            }
        }
        return true;
    }

    /**
     * 解析基准dimens文件
     *
     * @param baseDimenFilePath 源dimens文件路径
     */
    private static ArrayList<DimenBean> readBaseDimenFile(String baseDimenFilePath) {
        ArrayList<DimenBean> list = null;
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxparser = saxParserFactory.newSAXParser();
            InputStream inputStream = new FileInputStream(baseDimenFilePath);
            SAXReadHandler saxReadHandler = new SAXReadHandler();
            saxparser.parse(inputStream, saxReadHandler);
            list = saxReadHandler.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 生成对应的dimens目标文件
     *
     * @param list       源dimens数据
     * @param multiple   对应新文件需要乘以的系数
     * @param outPutFile 目标文件输出目录
     */
    private static void createDestinationDimens(ArrayList<DimenBean> list, float multiple, String outPutFile) {
        try {
            File targetFile = new File(outPutFile);
            if (targetFile.exists()) {
                targetFile.delete();
                System.out.println("旧文件 \"" + outPutFile + "\" 文件删除成功!");
            }
            System.out.println("正在生成 " + outPutFile + " 文件...");
            //创建SAXTransformerFactory实例
            SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            //创建TransformerHandler实例
            TransformerHandler handler = saxTransformerFactory.newTransformerHandler();
            //创建Transformer实例
            Transformer transformer = handler.getTransformer();
            //是否自动添加额外的空白
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //设置字符编码
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //添加xml版本，默认也是1.0
            transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
            //保存xml路径
            StreamResult result = new StreamResult(targetFile);
            handler.setResult(result);
            //创建属性Attribute对象
            AttributesImpl attributes = new AttributesImpl();
            attributes.clear();
            //开始xml
            handler.startDocument();
            //换行
            handler.characters("\n".toCharArray(), 0, "\n".length());
            //写入根节点resources
            handler.startElement("", "", SAXReadHandler.ELEMENT_RESOURCE, attributes);
            //集合大小
            int size = list.size();
            for (int i = 0; i < size; i++) {
                DimenBean dimenBean = list.get(i);
                //乘以系数，加上后缀
                String targetValue = countValue(dimenBean.value, multiple);
                attributes.clear();
                attributes.addAttribute("", "", SAXReadHandler.PROPERTY_NAME, "", dimenBean.name);

                //新dimen之前，换行、缩进
                handler.characters("\n".toCharArray(), 0, "\n".length());
                handler.characters("\t".toCharArray(), 0, "\t".length());

                //开始标签对输出
                handler.startElement("", "", SAXReadHandler.ELEMENT_DIMEN, attributes);
                handler.characters(targetValue.toCharArray(), 0, targetValue.length());
                handler.endElement("", "", SAXReadHandler.ELEMENT_DIMEN);
            }
            handler.endElement("", "", SAXReadHandler.ELEMENT_RESOURCE);
            handler.endDocument();
            System.out.println("新的 " + outPutFile + " 文件生成完成!");
        } catch (Exception e) {
            System.out.println("DK WARNING: " + outPutFile + " 文件生成失败!");
            e.printStackTrace();
        }
    }

    /**
     * 乘以系数
     *
     * @param oldValue 原字符串
     * @param multiple 目标dp/基准dp 得出的相乘系数
     *
     * @return 乘以系数后，且带单位的字符串
     */
    private static String countValue(String oldValue, float multiple) {
        //数据格式化对象
        DecimalFormat df = new DecimalFormat("0.00");
        String suffix = oldValue.substring(oldValue.length() - 2, oldValue.length());
        //乘以系数
        double temp = Double.parseDouble(oldValue.substring(0, oldValue.length() - 2)) * multiple;
        return df.format(temp) + suffix;
    }
}