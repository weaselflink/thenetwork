package de.bissell.thenetwork.gui

import javafx.application.Application
import javafx.geometry.Point3D
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Cylinder
import javafx.stage.Stage




class NetworkApplication : Application() {

    override fun start(stage: Stage) {
        val pane = Pane()
        val scene = Scene(pane, 200.0, 200.0)
        stage.title = "The Network"
        addNetworkNodes(pane)

        val camera = PerspectiveCamera(true)
        camera.farClip = 10000.0
        scene.camera = camera

        val cameraGroup = Group()
        cameraGroup.children.add(camera)
        pane.children.add(cameraGroup)

        camera.rotationAxis = Point3D(1.0, 0.0, 0.0)
        camera.rotate = 30.0
        cameraGroup.translateX = 50.0
        cameraGroup.translateY = 150.0
        cameraGroup.translateZ = -200.0


        stage.setScene(scene)
        stage.show()
    }

    private fun addNetworkNodes(root: Pane) {
        val nodePositions = listOf(
                Pair(20.0, 20.0),
                Pair(60.0, 20.0),
                Pair(100.0, 20.0),
                Pair(20.0, 60.0)
        )
        nodePositions.forEach {
            val cylinder = Cylinder(10.0, 10.0)
            val material = PhongMaterial(Color.RED)
            material.specularColor = Color.WHITE
            cylinder.material = material
            cylinder.rotationAxis = Point3D(1.0, 0.0, 0.0)
            cylinder.rotate = 90.0
            cylinder.translateX = it.first
            cylinder.translateY = it.second
            root.children.add(cylinder)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(NetworkApplication::class.java, *args)
}

