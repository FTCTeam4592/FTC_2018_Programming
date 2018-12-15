package org.firstinspires.ftc.teamcode.auto;

//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RobotBase;

@Autonomous
public class ColorTest extends RobotBase {

    @Override
    public void runOpMode() throws InterruptedException{
        setup();
        waitForStart();
        /*
        while(opModeIsActive()) {
            colorSensor.enableLed(false);
            int color = colorSensor.argb();

            telemetry.addData("color val: ", color);
            telemetry.update();

            if(color>=50000000){
                telemetry.addData("gold", color);
                telemetry.update();
            }
            else{
                telemetry.addData("white", color);
                telemetry.update();
            }
        }*/

    }
    }

