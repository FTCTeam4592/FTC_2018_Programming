package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ExpansionHubIMU;

/**
 * Created by user on 12/17/17.
 */
//@Autonomous(name="IMU Test")
public class IMUTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        ExpansionHubIMU imu = new ExpansionHubIMU(hardwareMap);
        imu.initialize();

        waitForStart();

        while(opModeIsActive())
        {
            imu.update(telemetry);

            telemetry.update();
            idle();
        }
    }
}
